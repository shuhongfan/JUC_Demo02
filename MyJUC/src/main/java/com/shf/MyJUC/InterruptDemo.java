package com.shf.MyJUC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo {
    /**
     * 通过volatile实现
     */
    static volatile boolean isStop = false;
    /**
     * 原子布尔型
     */
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println(Thread.currentThread().getName()+"\t atomicBoolean，程序停止");
                    break;
                }
                System.out.println("t1------hello atomicBoolean");
            }
        },"t1");
        t1.start();

        System.out.println("-----t1的默认中断标志位："+t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            /**
             * interrupt 中断此线程
             *  将t1的标志位设为true，t1将正常运行，不受影响
             *  interrupt并不能真正的中断线程，需要被调用的线程自己进行配合才行
             *  如果线程处于被阻塞的状态，在别的线程中调用当前线程的interrupt方法，
             *  那么线程将立即退出被阻塞的状态，并抛出一个InterruptedException 。
             */
            t1.interrupt();
        },"t2").start();
    }

    public static void m2(String[] args) {
        new Thread(()->{
            while (true){
                if (atomicBoolean.get()){
                    System.out.println(Thread.currentThread().getName()+"\t atomicBoolean，程序停止");
                    break;
                }
                System.out.println("t1------hello atomicBoolean");
            }
        },"t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            atomicBoolean.set(true);
        },"t2").start();
    }

    public static void m1(String[] args) {
        new Thread(()->{
            while (true){
                if (isStop){
                    System.out.println(Thread.currentThread().getName()+"\t isStop的值被修改为true，程序停止");
                    break;
                }
                System.out.println("t1------hello volatile");
            }
        },"t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            isStop=true;
        },"t2").start();
    }
}
