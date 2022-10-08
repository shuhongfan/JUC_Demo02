package com.shf.myjuc2.ThreadLocal;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 需求1： 5个销售卖房子，集团高层只关心销售数量的准确统计数
 *
 * 需求2： 5个销售卖完随机数房子，各自独立销售额度，自己业绩按提成走，分灶吃饭，丰衣足食
 */
public class ThreadLocalDemo {
    @SneakyThrows
    public static void main(String[] args) {
        House house = new House();
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    int size = new Random().nextInt(5) + 1;

                    for (int j = 0; j < size; j++) {
                        house.saleHouse();
                        house.saleValueByThreadLocal();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t 号销售卖出"+house.saleValue.get());
                } finally {
                    house.saleValue.remove();
                    countDownLatch.countDown();
                }
            },String.valueOf(i)).start();
        }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName()+"\t 共计卖出多少套："+house.saleCount);
    }
}

class House {
    int saleCount = 0;

    public synchronized void saleHouse() {
        ++saleCount;
    }

//    ThreadLocal<Integer> saleValue = new ThreadLocal<Integer>(){
//        @Override
//        protected Integer initialValue() {
//            return 0;
//        }
//    };

    ThreadLocal<Integer> saleValue = ThreadLocal.withInitial(() -> 0);

    public void saleValueByThreadLocal() {
        saleValue.set(1+saleValue.get());
    }
}
