package com.shf.MyJUC.volatiles;

import java.util.concurrent.TimeUnit;

public class VolatileSeeDemo {
    /**
     * volatile  即时可见性
     */
    static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ------come in");
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "\t ------flag 被设置为false");
        }, "t1").start();

        TimeUnit.SECONDS.sleep(2);

        flag = false;
    }
}
