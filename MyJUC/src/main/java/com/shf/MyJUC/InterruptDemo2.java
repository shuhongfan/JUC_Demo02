package com.shf.MyJUC;

import java.util.concurrent.TimeUnit;

public class InterruptDemo2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("------:" + i);
            }
            System.out.println("t1.interrupt线程的中断标识："+Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("t1线程默认的中断标识："+t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        t1.interrupt(); // true
        System.out.println("t1.interrupt线程的中断标识："+t1.isInterrupted());

//        中断一个不活动的线程不需要有任何效果。
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("t1.interrupt线程的中断标识："+t1.isInterrupted());

    }
}
