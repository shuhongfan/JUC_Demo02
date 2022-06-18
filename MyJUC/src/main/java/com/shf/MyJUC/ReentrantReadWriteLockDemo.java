package com.shf.MyJUC;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource();
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(()->{
                myResource.write(finalI+"", finalI+"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(()->{
                myResource.read(finalI+"");
            },String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        读锁未完成 写锁无法获得锁
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(()->{
                myResource.write(finalI+"", finalI+"");
            },"新写锁线程--》"+String.valueOf(i)).start();
        }
    }
}

class MyResource{
    Map<String, String> map = new HashMap<String, String>();
    Lock lock = new ReentrantLock();
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void write(String key,String value){
//        lock.lock();
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t"+"正在写入");
            map.put(key,value);
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println(Thread.currentThread().getName()+"\t"+"完成写入");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
//            lock.unlock();
            readWriteLock.writeLock().unlock();
        }
    }

    public void read(String key){
//        lock.lock();
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t"+"正在读取");
            String result = map.get(key);
            TimeUnit.MILLISECONDS.sleep(200);
            System.out.println(Thread.currentThread().getName()+"\t"+"完成读取："+result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
//            lock.unlock();
            readWriteLock.readLock().unlock();
        }
    }
}
