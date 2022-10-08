package com.shf.myjuc2.locks;

/**
 * Thread.interrupted() 静态方法
 * 判断线程是否中断并清除当前中断的状态
 *
 * 1.返回当前线程的中断状态，测试当前线程是否已经被中断
 * 2.将当前线程的中断状态清零并重新设置为FALSE，请求线程的中断状态
 */
public class InterruptDemo4 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"\t "+Thread.interrupted());
        System.out.println(Thread.currentThread().getName()+"\t "+Thread.interrupted());

        System.out.println("-------1");
        Thread.currentThread().interrupt();
        System.out.println("-------2");

        System.out.println(Thread.currentThread().getName()+"\t "+Thread.interrupted());
        System.out.println(Thread.currentThread().getName()+"\t "+Thread.interrupted());

        /**
         * 区别：
         * 中断的状态会根据传入的ClearInterrupted参数值确定是否重置
         *
         * 静态方法interrupted将会清除中断状态（传入的参数ClearInterrupted为true）
         * 实例方法isInterrupted则不会（传入FALSE）
         */
        Thread.interrupted();
        Thread.currentThread().isInterrupted();
    }
}

/**
 * 线程中断相关API方法的三大说明
 * interrupt  实例方法interrupt仅仅是设置线程的中断状态为true，发起一个协商而不会立刻停止线程
 * interrupted  静态方法，判断线程是否中断并清除当前中断的状态
 * isInterrupted   实例方法，判断当前线程是否被中断
 */
