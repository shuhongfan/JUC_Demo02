package com.shf.myjuc2.cas;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 题目:实现一个自旋锁,复习CAS思想
 * 自旋锁好处:循环比较获取没有类似wait的阻塞。
 * 通过CAS操作完成自旋锁, A线程先进来调用myLock方法自己持有锁5秒钟，B随后进来后发现
 * 当前有线程持有锁，所以只能通过自旋等待，直到A 释放锁后B随后抢到。
 *
 * CAS缺点
 *  循环时间长，开销大
 *  ABA问题
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t ----come in");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName()+"\t ---- task over,unlock");
    }

    @SneakyThrows
    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.lock();

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            spinLockDemo.unlock();
        },"A").start();

        TimeUnit.MILLISECONDS.sleep(500);

        new Thread(()->{
            spinLockDemo.lock();

            spinLockDemo.unlock();
        },"B").start();
    }
}
