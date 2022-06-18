package com.shf.MyJUC;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockDownGradingDemo {
    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

//        readLock.lock();
//        System.out.println("读取");
//        readLock.unlock();
//
//        writeLock.lock();
//        System.out.println("写入");
//        writeLock.unlock();

        writeLock.lock();
        System.out.println("写入");

        /**
         * 写后读 锁降级
         */
        readLock.lock();

        System.out.println("读取");
        writeLock.unlock();

        readLock.unlock();
    }
}
