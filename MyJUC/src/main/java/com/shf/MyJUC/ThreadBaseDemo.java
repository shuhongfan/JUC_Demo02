package com.shf.MyJUC;

import java.util.concurrent.TimeUnit;

public class ThreadBaseDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 开始运行，");
            System.out.println(Thread.currentThread().isDaemon()?"守护线程":"用户线程");
        }, "t1");
//        设置为守护线程
        t1.setDaemon(true);
        t1.start();

        Object o = new Object();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t2").start();

        System.out.println(Thread.currentThread().getName()+"\t --end 主线程");
    }
}
