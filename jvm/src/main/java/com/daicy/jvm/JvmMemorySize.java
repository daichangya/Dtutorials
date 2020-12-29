package com.daicy.jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author: create by daichangya
 * @version: v1.0
 * @description: com.daicy.jvm
 * @date:12/28/20
 */
public class JvmMemorySize {
    // 关闭指针压缩-XX:-UseCompressedOops
    public static void main(String []f) {
        System.out.println(ClassLayout.parseInstance(new Integer(2)).toPrintable());
        System.out.println(ClassLayout.parseInstance(new Long(2)).toPrintable());
        System.out.println(ClassLayout.parseInstance(new MyIntArray()).toPrintable());
        System.out.println(ClassLayout.parseInstance(new MyStringArray()).toPrintable());
        System.out.println(ClassLayout.parseInstance(new MyLong()).toPrintable());
        System.out.println(ClassLayout.parseInstance(new int[]{1,2,3}).toPrintable());
        System.out.println(ClassLayout.parseInstance(new String[]{"1","2","3"}).toPrintable());
        System.out.println(ClassLayout.parseInstance(new MyLong[]{new MyLong(), new MyLong(), new MyLong()}).toPrintable());
    }

    private static class MyIntArray {
        public int[] intArray;
    }

    private static class MyStringArray {
        public String[] strings;
    }

    private static class MyLong {
        public volatile long usefulVal;
        public volatile Long anotherVal;
        public MyRef myRef;
    }

    private static class MyRef {
        Integer integer = new Integer(15);
    }
}