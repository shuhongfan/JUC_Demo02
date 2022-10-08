package com.shf.myjuc2.ThreadLocal;

import lombok.SneakyThrows;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * -Xms10m -Xmx10m
 *
 * ThreadLocal是-一个壳子，真正的存储结构是ThreadLocal里有ThreadLocalMap这么个内部类，每个Thread对象维护着一个ThreadLocalMap的引用
 * ThreadLocalMap是ThreadLocal的内部类，用Entry来进行存储。
 * 1)调用ThreadLocal的set()方法时，实际上就是往ThreadLocalMap设置值，key 是ThreadLocal对象，值Value 是传递进来的对象
 * 2)调用ThreadLocal的get()方法时，实际上就是往ThreadLocalMap获取值，key是ThreadLocal对象
 * ThreadLocal本身并不存储值(ThreadLocal是-个壳子)， 它只是自己作为一个key来让线程从ThreadLocalMap获取value.
 * 正因为这个原理，所以ThreadLocal能够实现 “数据隔离”，获取当前线程的局部变量值，不受其他线程影响~
 */
public class ReferenceDemo {
    @SneakyThrows
    public static void main(String[] args) {
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();

        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);

        System.out.println(phantomReference.get());

        ArrayList<byte[]> list = new ArrayList<>();
        new Thread(()->{
            while (true) {
                list.add(new byte[1 * 1024 * 1024]);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(phantomReference.get()+"\t"+" list add ok");
            }
        },"t1").start();


        new Thread(()->{
            while (true) {
                Reference<? extends MyObject> reference = referenceQueue.poll();
                if (reference != null) {
                    System.out.println("有虚引用对象加入队列");
                    break;
                }
            }
        },"t1").start();
    }

    /**
     * 弱引用：gc就回收
     */
    private static void WeakReference() {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        System.out.println("------- gc before 内存够用："+weakReference.get());

        System.gc();

        System.out.println("------- gc before 内存够用："+weakReference.get());
    }

    /**
     * 软引用：内存不够回收
     * @throws InterruptedException
     */
    private static void SoftReference() throws InterruptedException {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
        System.out.println("--------softReference:"+softReference.get());


        try {
            byte[] bytes = new byte[20 * 1024 * 1024];
        } finally {
            System.out.println("-------gc after 内存不够"+softReference.get());
        }

        System.gc();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("--------softReference:"+softReference.get());
    }

    /**
     * 强引用：死都不回收
     */
    private static void StrongReference() {
        MyObject myObject = new MyObject();
        System.out.println("gc before:" + myObject);

        myObject = null;
        System.gc();
        System.out.println("gc after:" + myObject);
    }
}


class MyObject {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("----------invoke finalize method!!!");
    }
}