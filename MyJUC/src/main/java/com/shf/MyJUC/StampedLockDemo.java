package com.shf.MyJUC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {

    static int number = 37;
    static StampedLock stampedLock = new StampedLock();

    public void write(){
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName()+"\t写线程准备修改");
        try {
            number = number+13;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
        System.out.println(Thread.currentThread().getName()+"\t写线程结束修改");
    }

    /**
     * 悲观读取，读取没有完成无法获得锁
     */
    public void read(){
        long stamp = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName()+"\t come in readLock");
        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName()+"\t正在读取中....");
        }

        try {
            int result = number;
            System.out.println(Thread.currentThread().getName()+"\t获得成员变量值result:"+result);
            System.out.println("写线程没有修改成功，读锁时候写锁无法介入，传统的读写互斥");
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    /**
     * 乐观读，读的过程中也允许获取写锁介入
     */
    public void tryOptimisticRead(){
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        System.out.println("4秒前stampedLock.validate方法值（true无效，。false有修改）\t"+stampedLock.validate(stamp));
        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName()+"\t"+"正在读取..."
                    +i+" 秒后stampedLock.validate方法值（true无修改，false有修改）"+"\t"+stampedLock.validate(stamp));
        }

        if (!stampedLock.validate(stamp)){
            System.out.println("有人修改过----有写操作");
            stamp =  stampedLock.readLock();
        }
        try {
            System.out.println("从乐观读 升级为 悲观读");
            result = number;
            System.out.println("重新悲观读后result： "+result);
        }finally {
            stampedLock.unlockRead(stamp);
        }
        System.out.println(Thread.currentThread().getName()+"\t"+" finally value:"+result);
    }

    public static void m1(String[] args) {
        StampedLockDemo stampedLockDemo = new StampedLockDemo();
        new Thread(()->{
            stampedLockDemo.read();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t come in");
                stampedLockDemo.write();
            }).start();
        }).start();
    }

    public static void main(String[] args) {
        StampedLockDemo resource = new StampedLockDemo();

        new Thread(()->{
            resource.tryOptimisticRead();
        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t"+"come in");
            resource.write();
        }).start();
    }
}
