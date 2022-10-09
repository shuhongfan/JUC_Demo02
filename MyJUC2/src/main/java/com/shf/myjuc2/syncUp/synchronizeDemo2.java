package com.shf.myjuc2.syncUp;

import org.openjdk.jol.info.ClassLayout;

/**
 * -XX:BiasedLockingStartupDelay=0
 */
public class synchronizeDemo2 {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        new Thread(()->{
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        },"t1").start();

    }
}
