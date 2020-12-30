package com.daicy.jvm;

import org.openjdk.jol.info.ClassLayout;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: create by daichangya
 * @version: v1.0
 * @description: com.daicy.jvm
 * @date:12/29/20
 */
public class LockClient {

    static class User {
        public String name;
        public byte age;
    }

    public static void main(String[] args) throws InterruptedException {
        List<User> userList = new CopyOnWriteArrayList<User>();
        for (int i = 0; i < 10000; i++) {
            userList.add(new User());
            Thread.sleep(200);
        }
        userList.set(5,new User());
    }
}
