package com.shf.myjuc2.stamplock;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * 所有获取锁的方法，都返回一个邮戳(Stamp) ，Stamp为零表示获取失败，其余都表示成功;
 * 所有释放锁的方法，都需要- -个邮戳( Stamp)，这个Stamp必须是和成功获取锁时得到的Stamp- - 致;
 * StampedLock是不可重入的，危险(如果一.个线程已经持有 了写锁，再去获取写锁的话就会造成死锁)
 *
 *
 * 读的过程中也允许写锁介入
 *
 * StampedLock不支持重入，没有Re开头
 * StampedLock的悲观读锁和写锁都不支持条件变量(Condition) ，这个也需要注意。
 * 使用StampedLock-定不要调用中断操作，即不要调用interrupt()方法
 */
public class StampLockDemo {
    static int number = 37;
    static StampedLock stampedLock = new StampedLock();

    public void write() {
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName()+"\t 写线程准备修改");
        try {
            number = number + 13;
        } finally {
            stampedLock.unlock(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t 写线程结束修改");
    }


    @SneakyThrows
    public void read() {
        long stamp = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName()+"\t come in readlock ");
        for (int i = 0; i < 4; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName()+"\t 正在读取中....");
        }

        try {
            int result = number;
            System.out.println(Thread.currentThread().getName() + "\t 获得成员变量值result：" + result);
            System.out.println("写线程没有修改成功，读锁时候写锁无法介入，传统的读写互斥");
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    @SneakyThrows
    public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;

        System.out.println("4秒前stampedLock.validate方法值(true无修改，false有修改)\t"+stampedLock.validate(stamp));

        for (int i = 0; i < 4; i++) {
            TimeUnit.SECONDS.sleep(1);

            System.out.print(Thread.currentThread().getName()+"\t正在读取"+i);
            System.out.println("秒stampedLock.validate方法值(true无修改，false有修改)\t"+stampedLock.validate(stamp));

            if (!stampedLock.validate(stamp)) {
                System.out.println("有人修改过-----有写操作");

                stamp = stampedLock.readLock();

                try {
                    System.out.println("从乐观读 升级为 悲观读");
                    result = number;
                    System.out.println("重新悲观读后result:" + result);
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t finally value：" + result);
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        StampLockDemo stampLockDemo = new StampLockDemo();

//        new Thread(()->{
//            stampLockDemo.read();
//        }).start();
//
//        TimeUnit.SECONDS.sleep(1);
//
//        new Thread(()->{
//            System.out.println(Thread.currentThread().getName()+"\t ----- come in");
//            stampLockDemo.write();
//        }).start();

        new Thread(()->{
            stampLockDemo.tryOptimisticRead();
        },"readThread").start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t --- come in");
            stampLockDemo.write();
        },"writeThread").start();
    }

}
