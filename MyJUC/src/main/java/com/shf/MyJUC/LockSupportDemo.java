package com.shf.MyJUC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t  ----- come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t ---- 被唤醒"+System.currentTimeMillis());
        }, "t1");
        t1.start();

//        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName()+"\t ---- 发出通知"+System.currentTimeMillis());
        },"t2").start();
    }


    public static void m2(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            lock.lock();
            try
            {
                System.out.println(Thread.currentThread().getName()+"\t ----come in");
                condition.await();
                System.out.println(Thread.currentThread().getName()+"\t ----被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"t1").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            lock.lock();
            try
            {
                condition.signal();
                System.out.println(Thread.currentThread().getName()+"\t ----发出通知");
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }


    public static void syncWaitNotify(String[] args) {
        Object objectLock = new Object();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            synchronized (objectLock){
                System.out.println(Thread.currentThread().getName()+"\t  ----come in");
                try {
                    objectLock.wait();  // java.lang.IllegalMonitorStateException 必须包裹在同步代码块中
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t---被唤醒");
            }
        },"t1").start();


        new Thread(()->{
            synchronized (objectLock){
                objectLock.notify();
                System.out.println(Thread.currentThread().getName()+"\t----发出通知");
            }
        },"t2").start();
    }
}
