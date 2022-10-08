package com.shf.myjuc2.locks;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * 当前线程的中断标识位true，是不是线程就立刻停止？
 */
public class InterruptDemo3 {
    @SneakyThrows
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " 程序停止");
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    /**
                     * 为什么要在异常出，再调用一次？？
                     * 1.中断标志位默认为false
                     * 2. t2---->t1发送中断协商，t2调用t1.interupt（），中断标志位true
                     * 3. 中断标志位true，正常情况，程序停止
                     * 4. 中断标志位，异常情况，InterruptException，将会把中断的状态清除，并且将收到InterruptException，中断标志位为FALSE，导致无限循环
                     * 5. 所以在catch块中，需要再次给中断标志位设置为true，2次调用程序才停止
                     *
                     * 中断只是一种协商机制，修改中断标识位仅此而已，不是立刻stop打断
                     */
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }

                System.out.println("------ hello InterruptDemo3");
            }
        }, "t1");
        t1.start();

        TimeUnit.SECONDS.sleep(3);

        new Thread(()->{
            t1.interrupt();
        },"t2").start();
    }
}
