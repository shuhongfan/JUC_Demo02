package com.shf.myjuc2.locks;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo {
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    @SneakyThrows
    public static void main(String[] args) {
        m3_Interrupt();
    }

    /**
     * 对于一个Interrupt时：
     *
     * 如果线程处于正常活动状态，那么会将该线程的中断标志设置为true，仅此而已。
     * 设置中断标志的线程将继续正常运行，不受影响。
     * 所以，interrupt并不能真正的中断线程，需要被调用的线程自己进行配合才行。
     *
     * 如果线程处于被阻塞状态（例如处于sleep，wait，join等状态），在别的线程中调用当前线程对象的interrupt方法，
     * 那么线程将立即退出阻塞状态，并抛出InterrupedException异常
     * @throws InterruptedException
     */
    private static void m3_Interrupt() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t isInterrupted() 被修改为true，程序终止");
                    break;
                }
                System.out.println("t1--------hello interupt api");
            }
        }, "t1");
        t1.start();

        TimeUnit.MILLISECONDS.sleep(20);

        new Thread(()->{
            t1.interrupt();
        },"t2").start();
    }

    private static void m2_atomicBoolean() throws InterruptedException {
        new Thread(()->{
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName()+"\t atomicBoolean被修改为true，程序停止");
                    break;
                }
                System.out.println("t1 --- hello atomicBoolean");
            }
        },"t1").start();

        TimeUnit.MILLISECONDS.sleep(20);

        new Thread(()->{
            atomicBoolean.set(true);
        },"t2").start();
    }

    private static void m1_volatile() throws InterruptedException {
        new Thread(()->{
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true，程序终止");
                    break;
                }
                System.out.println("t1 ---- hello volatile");
            }
        },"t1").start();

        TimeUnit.MILLISECONDS.sleep(20);

        new Thread(()->{
            isStop = true;
        },"t2").start();
    }
}
