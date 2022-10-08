package com.shf.myjuc2.locks;

public class DeadLockDemo {
    public static void main(String[] args) {
        final Object objectA = new Object();
        final Object objectB = new Object();

        new Thread(()->{
            synchronized (objectA) {
                System.out.println(Thread.currentThread().getName()+"\t成功获取锁A");

                synchronized (objectB) {
                    System.out.println(Thread.currentThread().getName()+"\t成功获取锁B");
                }
            }
        },"t1").start();

        new Thread(()->{
            synchronized (objectB) {
                System.out.println(Thread.currentThread().getName()+"\t成功获取锁B");

                synchronized (objectA) {
                    System.out.println(Thread.currentThread().getName()+"\t成功获取锁A");
                }
            }
        },"t2").start();
    }
}
