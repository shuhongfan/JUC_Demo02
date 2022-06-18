package com.shf.MyJUC;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {
    public static void main(String[] args) {
        final Object objA=new Object();
        final Object objB=new Object();

        new Thread(()->{
            synchronized (objA){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有A锁希望得到B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {

                }

                synchronized (objB){
                    System.out.println(Thread.currentThread().getName()+"\t 成功获得B锁");
                }
            }
        },"A").start();

        new Thread(()->{
            synchronized (objB){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有B锁希望得到A锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {

                }

                synchronized (objA){
                    System.out.println(Thread.currentThread().getName()+"\t 成功获得A锁");
                }
            }
        },"B").start();
    }
}
