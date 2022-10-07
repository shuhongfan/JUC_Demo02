package com.shf.myjuc2.base;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class DaemonDemo {
    @SneakyThrows
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t正在运行" + (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程"));

            while (true) {

            }
        }, "t1");

        t1.setDaemon(true);
        t1.start();

        TimeUnit.SECONDS.sleep(3);

        System.out.println(Thread.currentThread().getName()+"\t end 用户线程");
    }
}
