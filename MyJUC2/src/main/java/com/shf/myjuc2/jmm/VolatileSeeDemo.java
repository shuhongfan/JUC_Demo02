package com.shf.myjuc2.jmm;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class VolatileSeeDemo {
    static volatile boolean flag = true;

    @SneakyThrows
    public static void main(String[] args) {

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t ---- come in");

            while (flag) {

            }

            System.out.println(Thread.currentThread().getName()+"\t ---- flag被设置为FALSE，程序停止");
        },"t1").start();


        TimeUnit.SECONDS.sleep(2);

        flag = false;
        System.out.println(Thread.currentThread().getName() + "\t 修改完成flag：" + flag);
    }
}
