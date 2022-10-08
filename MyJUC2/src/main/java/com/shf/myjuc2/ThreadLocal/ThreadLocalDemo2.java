package com.shf.myjuc2.ThreadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal 线程局部变量
 * 因为每个Thread内存有自己的实例副本并且该副本只由当前线程自己使用
 * 既然其他Thread不可访问，那就不存在多线程之间共享的问题
 * 统一设置初始值，但是每个线程对这个值的修改都是各自线程相互独立的
 *
 * 一句话？ 如何才能不争抢？
 *      1. 加入synchronized或者Lock控制资源的访问顺序
 *      2. 人手一份，大家各自安好，没必要抢夺
 *
 * 一个线程拥有一个ThreadLocalMap,一个ThreadLocalMap可以有多个ThreadLocal
 *
 *
 * 什么事内存泄漏：不再被使用的对象或者变量占用的内存不能被回收
 */
public class ThreadLocalDemo2 {
    public static void main(String[] args) {
        MyData myData = new MyData();

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {
            for (int i = 0; i < 10; i++) {
                threadPool.submit(() -> {
                    try {
                        Integer beforeInt = myData.threadLocal.get();
                        myData.add();
                        Integer afterInt = myData.threadLocal.get();
                        System.out.println(Thread.currentThread().getName() + "\t beforeInt:" + beforeInt + "\t afterInt:" + afterInt);
                    } finally {
                        myData.threadLocal.remove();
                    }
                });
            }
        } finally {
            threadPool.shutdown();
        }
    }
}

class MyData {
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public void add() {
        threadLocal.set(threadLocal.get() + 1);
    }

}
