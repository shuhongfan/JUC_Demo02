package com.shf.myjuc2.locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReEntryLockDemo {
    public static void main(String[] args) {
        final Object object = new Object();

        new Thread(()->{
            synchronized (object) {
                System.out.println(Thread.currentThread().getName()+"\t----外层调用");
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName()+"\t-----中层调用");
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName()+"\t----内层调用");
                    }
                }
            }
        },"t1").start();

        ReentrantLock lock = new ReentrantLock();
        new Thread(()->{
            lock.lock();

            try {
                System.out.println(Thread.currentThread().getName()+"\t ----- come in 外层调用");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName()+"\t ---- come in 内层调用");
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        },"t1").start();


        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t ---- come in 外层调用");
            } finally {
                lock.unlock();
            }
        },"t2").start();
    }
}
