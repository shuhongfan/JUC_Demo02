package com.shf.myjuc2.locks;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LockSupport是用来创建锁和其他同步类的基本线程阻塞原语
 * LockSupport是一个线程阻塞工具类，所有的方都是静态的，可以让线程在任意位置阻塞，阻塞之后也有对应的唤醒方法。
 * 归根结底，LockSupport调用的UNSafe中的native代码、
 *
 * LockSupport提供park和unpark方法实现线程阻塞和接触线程阻塞的工程
 * LockSupport和每个使用它的线程都有一个许可permit关联
 * 每个线程都有一个相关的permit，permit最多只有一个，重复调用unpark也不会积累凭证
 *
 * 线程阻塞需要消耗凭证（permit），这个凭证最多只有1个
 *
 * 当调用park方法时
 *      如果有凭证，则会直接消耗掉这个凭证然后正常退出
 *      如果没有凭证，就必须阻塞等待凭证可用
 * 而unpark相反，它会增加一个凭证，但凭证最多只能有1个，累加无效
 */
public class LockSupportDemo {
    @SneakyThrows
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ------ come in"+System.currentTimeMillis());
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t --------被唤醒"+System.currentTimeMillis());
        }, "t1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName()+"\t --------发出通知");
        },"t2").start();
    }

    private static void lockAwaitSignal() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ---- come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t ------ 被唤醒");
            } catch (InterruptedException e) {

            } finally {
                lock.unlock();
            }
        },"t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            lock.lock();

            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName()+"\t ---- 发出通知");
            } finally {
                lock.unlock();
            }
        },"t2").start();
    }

    private static void syncWaitNotify() throws InterruptedException {
        Object objectLock = new Object();

        new Thread(()->{
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName()+"\t ---- come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "\t----被唤醒");
            }
        },"t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println(Thread.currentThread().getName()+"\t---发出通知");
            }
        },"t2").start();
    }
}
