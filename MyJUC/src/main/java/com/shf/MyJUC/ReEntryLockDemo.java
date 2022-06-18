package com.shf.MyJUC;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReEntryLockDemo {
    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName()+"\t----come in");
        m2();
        System.out.println(Thread.currentThread().getName()+"\t----end");
    }

    public synchronized void m2(){
        System.out.println(Thread.currentThread().getName()+"\t----come in");
        m3();
        System.out.println(Thread.currentThread().getName()+"\t----end");
    }

    public synchronized void m3(){
        System.out.println(Thread.currentThread().getName()+"\t----come in");
        System.out.println(Thread.currentThread().getName()+"\t----end");
    }

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
//        ReEntryLockDemo reEntryLockDemo = new ReEntryLockDemo();
//        new Thread(()->{
//            reEntryLockDemo.m1();
//        },"a").start();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t---come in 外层调用");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName()+"\t---come in 中层调用");
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName()+"\t---come in 内层调用");
                    } finally {
                        lock.unlock();
                    }
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        },"t1").start();
    }



    public static void reEntry1(String[] args) {
        final Object obj = new Object();

        /**
         * 可重入锁
         */
        new Thread(()->{
            synchronized (obj){
                System.out.println(Thread.currentThread().getName()+"\t----外层调用");
                synchronized (obj){
                    System.out.println(Thread.currentThread().getName()+"\t----中层调用");
                    synchronized (obj){
                        System.out.println(Thread.currentThread().getName()+"\t----内层调用");
                    }
                }
            }
        },"t1").start();
    }
}
