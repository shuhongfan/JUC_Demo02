package com.shf.myjuc2.syncUp;

/**
 * 锁粗化
 */
public class LockBigDemo {
    static Object objectLock = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (objectLock) {
                System.out.println("11111");
            }

            synchronized (objectLock) {
                System.out.println("22222");
            }

            synchronized (objectLock) {
                System.out.println("33333");
            }

            synchronized (objectLock) {
                System.out.println("44444");
            }


            synchronized (objectLock) {
                System.out.println("11111");
                System.out.println("22222");
                System.out.println("33333");
                System.out.println("44444");
            }
        }).start();
    }
}
