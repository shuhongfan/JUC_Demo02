package com.shf.myjuc2.atomic;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    public static final int SIZE = 50;

    @SneakyThrows
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        for (int i = 0; i < 50; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < 1000; j++) {
                        myNumber.addPlusPlus();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            },String.valueOf(i)).start();
        }

//        等上面50个线程全部计算完成后，再去获取最终值
//        TimeUnit.SECONDS.sleep(2);
        countDownLatch.await();

        System.out.println(Thread.currentThread().getName()+"\t result:"+myNumber.atomicInteger.get());
    }
}


class MyNumber {
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus() {
        atomicInteger.getAndIncrement();
    }
}