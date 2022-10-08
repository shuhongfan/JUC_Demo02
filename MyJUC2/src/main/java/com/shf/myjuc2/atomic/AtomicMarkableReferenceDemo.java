package com.shf.myjuc2.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference
 *          携带版本号的引用类型原子类，可以解决ABA问题
 *          version号+1
 *          解决修改过几次
 *          状态戳原子引用
 *
 * AtomicMarkableReference
 *          原子更新带有标记位的引用类型对象
 *          一次，false，true
 *          解决是否修改过
 *          状态戳（true/false）原子引用
 */
public class AtomicMarkableReferenceDemo {
    static AtomicMarkableReference markableReference = new AtomicMarkableReference(100, false);

    public static void main(String[] args) {
        new Thread(()->{
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t 默认标识：" + marked);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean isSet = markableReference.compareAndSet(100, 1000, marked, !marked);
            System.out.println(Thread.currentThread().getName() + "\t" + "t1线程result：" + isSet);
            System.out.println(Thread.currentThread().getName() + "\t" + markableReference.isMarked());
            System.out.println(Thread.currentThread().getName() + "\t" + markableReference.getReference());
            System.out.println("--------------------------------------------");
        },"t1").start();

        new Thread(()->{
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t 默认标识：" + marked);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            boolean isSet = markableReference.compareAndSet(100, 2000, marked, !marked);
            System.out.println(Thread.currentThread().getName() + "\t" + "t2线程result：" + isSet);
            System.out.println(Thread.currentThread().getName() + "\t" + markableReference.isMarked());
            System.out.println(Thread.currentThread().getName() + "\t" + markableReference.getReference());
        },"t2").start();

    }
}

