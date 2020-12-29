package com.daicy.jvm;


import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import com.daicy.jvm.LockClient.User;

/**
 * @author: create by daichangya
 * @version: v1.0
 * @description: com.daicy.jvm
 * @date:12/29/20
 */
public class LockClientTest {
    @Test
    public void noLock() {
        User user = new User();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }

    @Test
    public void biasedLocking() {
        // 先睡眠5秒，保证开启偏向锁
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) { // -XX:-UseBiasedLocking
            e.printStackTrace(); // -XX:BiasedLockingStartupDelay=0
        }
        User user = new User();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }

    @Test
    public void synchronizedLocking() {
        // 先睡眠5秒，保证开启偏向锁
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) { // -XX:-UseBiasedLocking
            e.printStackTrace(); // -XX:BiasedLockingStartupDelay=0
        }
        System.out.println(Thread.currentThread().getId());
//        System.out.println(Integer.toBinaryString(System.identityHashCode(Thread.currentThread())));
        User user = new User();
        synchronized (user) {
            System.out.println(ClassLayout.parseInstance(user).toPrintable());
        }
    }

    @Test
    public void lightWeightLock() {
        System.out.println(Integer.toBinaryString(System.identityHashCode(Thread.currentThread())));
        User user = new User();
        synchronized (user) {
            System.out.println(ClassLayout.parseInstance(user).toPrintable());
        }
    }

    @Test
    public void heavyWeightLock() {
        User user = new User();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        Thread t1 = new Thread(() -> {
            synchronized (user) {
                try {
                    Thread.sleep(5000);// 睡眠，创造竞争条件
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        Thread t2  = new Thread(() -> {
            synchronized (user) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }

    @Test
    public void cancelBiasedLocking() {
        // 先睡眠5秒，保证开启偏向锁
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) { // -XX:-UseBiasedLocking
            e.printStackTrace(); // -XX:BiasedLockingStartupDelay=0
        }
        User user = new User();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        System.out.println(user.hashCode());
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        synchronized (user) {
            System.out.println(ClassLayout.parseInstance(user).toPrintable());
        }
    }

    @Test
    public void biasedLockToLightWeightLock() throws InterruptedException {
        // 先睡眠5秒，保证开启偏向锁
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) { // -XX:-UseBiasedLocking
            e.printStackTrace(); // -XX:BiasedLockingStartupDelay=0
        }
        User user = new User();
        Thread t1 = new Thread(() -> {
            synchronized (user) {
                System.out.println(ClassLayout.parseInstance(user).toPrintable());
            }
        });
        t1.start();
        t1.join(); // 确保t1执行完了再执行当前主线程
        synchronized (user) {
            System.out.println(ClassLayout.parseInstance(user).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }

    @Test
    public void lightWeightToheavyWeightLock() {
        //-XX:-UseBiasedLocking 关闭偏向锁
        User user = new User();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        Thread t1 = new Thread(() -> {
            synchronized (user) {
                try {
                    Thread.sleep(5000);// 睡眠，创造竞争条件
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        Thread t2  = new Thread(() -> {
            synchronized (user) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }

}
