package com.shf.myjuc2.atomic;


import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 这是一个基于反射的工具类，它能对指定类的【指定的volatile引用字段】进行【原子更新】。(注意这个字段不能是private的)
 * 简单理解：就是对某个类中，被volatile修饰的字段进行原子更新。
 *
 * 此类接收三个参数：
 * 1、字段所在的类。
 * 2、字段的类型。
 * 3、更新字段的内容。
 */
public class AtomicReferenceFiledUpdaterDemo {
    public static void main(String[] args) {
        MyVar myVar = new MyVar();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                myVar.init(myVar);
            },String.valueOf(i)).start();
        }
    }
}

class MyVar {
    public volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyVar, Boolean> referenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyVar.class, Boolean.class, "isInit");

    @SneakyThrows
    public void init(MyVar myVar) {
        boolean isSet = referenceFieldUpdater.compareAndSet(myVar, Boolean.FALSE, Boolean.TRUE);
        if (isSet) {
            System.out.println(Thread.currentThread().getName() + "\t --- start init,need 2 seconds");
            TimeUnit.SECONDS.sleep(3);
            System.out.println(Thread.currentThread().getName() + "\t ---- over init");
        } else {
            System.out.println(Thread.currentThread().getName()+"\t --- 已经有线程在进行初始化操作");
        }
    }
}
