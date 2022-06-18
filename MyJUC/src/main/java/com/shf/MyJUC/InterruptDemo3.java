package com.shf.MyJUC;

import java.util.concurrent.TimeUnit;

public class InterruptDemo3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t 中断标志位:" +
                            Thread.currentThread().isInterrupted()+"程序停止");
                    break;
                }
                /**
                 * 如果此线程在调用Object类的wait() 、 wait(long)或wait(long, int)方法或join() 、
                 * join(long) 、 join(long, int) int) 方法时被阻塞, sleep(long) ,
                 * or sleep(long, int) , 这个类的方法，那么它的中断状态将被清除，
                 * 它会收到一个InterruptedException 。
                 */
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // true
                    throw new RuntimeException(e);
                }
                System.out.println("-----hello InterruptDemo3");
            }
        }, "t1");
        t1.start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            t1.interrupt();
        },"t2").start();
    }
}
