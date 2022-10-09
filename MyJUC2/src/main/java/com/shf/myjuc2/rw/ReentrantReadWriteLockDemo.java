package com.shf.myjuc2.rw;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 一体两面、读写互斥、读读共享
 *
 * 写锁的降级，降级成为了读锁
 * 1 如果同一个线程持有了写锁，在没有释放写锁的情况下，它还可以继续获得读锁。这就是写锁的降级，降级成为了读锁。
 * 2 规则惯例，先获取写锁，然后获取读锁，再释放写锁的次序。
 * 3 如果释放了写锁，那么就完全转换为读锁。
 */
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource();

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(()->{
                myResource.write(finalI + "", finalI + "");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(()->{
                myResource.read(finalI+"");
            },String.valueOf(i)).start();
        }
    }
}

class MyResource {
    Map<String, String> map = new HashMap<String, String>();

    Lock lock = new ReentrantLock();

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @SneakyThrows
    public void write(String key, String value) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入");
            map.put(key, value);

            TimeUnit.MILLISECONDS.sleep(500);

            System.out.println(Thread.currentThread().getName()+"\t 完成写入");
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public void read(String key) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取");
            String val = map.get(key);

            TimeUnit.MILLISECONDS.sleep(500);

            System.out.println(Thread.currentThread().getName()+"\t 完成读取"+val);
        } finally {
            lock.unlock();
        }
    }
}
