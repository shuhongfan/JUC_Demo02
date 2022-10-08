package com.shf.myjuc2.locks;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class InterruptDemo2 {
    @SneakyThrows
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 3000; i++) {
                System.out.println("------------:"+i);
            }
            System.out.println("t1线程调用Interrupt后的中断标识02"+Thread.currentThread().isInterrupted());

        }, "t1");
        t1.start();

        System.out.println("t1线程默认的中断标识：" + t1.isInterrupted());

        TimeUnit.MILLISECONDS.sleep(2);

        t1.interrupt();
        System.out.println("t1线程调用Interrupt后的中断标识01"+t1.isInterrupted());

        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("t1线程调用Interrupt后的中断标识03"+t1.isInterrupted());
    }
}
