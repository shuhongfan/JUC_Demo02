package com.shf.MyJUC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterDemo {
    public static void main(String[] args) {
        MyVar myVar = new MyVar();

        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                try {
                    myVar.init(myVar);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}


class MyVar
{
    public volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyVar,Boolean> referenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(
                    MyVar.class,
                    Boolean.class,
                    "isInit");

    public void init(MyVar myVar) throws InterruptedException {
        if (referenceFieldUpdater.compareAndSet(myVar,Boolean.FALSE,Boolean.TRUE)) {
            System.out.println(Thread.currentThread().getName()+"\t"+"-----start init, need 2 seconds");

            TimeUnit.SECONDS.sleep(3);

            System.out.println(Thread.currentThread().getName()+"\t----- over init");
        } else {
            System.out.println(Thread.currentThread().getName()+"\t"+"----- 已经有线程在进行初始化工作。。。。");
        }
    }
}