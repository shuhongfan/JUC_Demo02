package com.shf.MyJUC;

public class InterruptDemo4 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());
        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());
        System.out.println("-------1");

        Thread.currentThread().interrupt();

        System.out.println("------2");

        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());

        /**
         * Thread.interrupted() 静态方法
         * 测试当前线程是否被中断。通过该方法清除线程的中断状态。
         * 换句话说，如果这个方法被连续调用两次，第二次调用将返回 false
         */
        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());

        Thread.currentThread().interrupt();
    }
}
