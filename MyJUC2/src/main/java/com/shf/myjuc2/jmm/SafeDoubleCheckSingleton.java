package com.shf.myjuc2.jmm;

public class SafeDoubleCheckSingleton {
//    通过volatile声明，实现线程安全的延迟初始化
    private volatile static SafeDoubleCheckSingleton singleton;

//    私有化构造方法
    private SafeDoubleCheckSingleton() {

    }

//    双重锁设计
    public static SafeDoubleCheckSingleton getInstance() {
        if (singleton == null) {
//            多线程并发创建对象时，会通过加锁保证只有一个线程能够创建对象
            synchronized (SafeDoubleCheckSingleton.class) { // 减少锁的力度
                if (singleton == null) {
//                    隐患：多线程环境下，由于重排序，该对象可能还未完成初始化就被其他线程读取
//                    解决隐患原理：利用volatile，禁止 初始化对象和设置singleton指向内存空间的重排序
                    System.out.println("SafeDoubleCheckSingleton实例化");
                    singleton = new SafeDoubleCheckSingleton(); // 分配内存空间、初始化对象、将对象指向分配的内存空间
                }
            }
        }
//        对象创建完毕，指向getInstance将不需要获取锁，直接返回创建对象
        return singleton;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t"+SafeDoubleCheckSingleton.getInstance());
            }).start();
        }
    }
}
