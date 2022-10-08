package com.shf.myjuc2.locks;

class Book extends Object {

}

/**
 * 什么是管程monitor 通过C底层源语了解
 * 在HotSpot虚拟机中，monitor采用ObjectMonitor实现，owner属性指向持有ObjectMonitor对象的线程
 *      每个对象天生都带着一个对象监视器
 *      每一个被锁住的对象都会和Monitor关联起来
 */
public class LockSyncDemo {
    Object object = new Object();

    public void m1() {
        synchronized (object) {
            System.out.println("-----hello sync");
            throw new RuntimeException("-exp");
        }
    }

    public synchronized void m2() {
        System.out.println("------hello sync m2");
    }

    public static void main(String[] args) {

    }
}
