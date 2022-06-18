package com.shf.MyJUC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class AccumulatorCompareDemo {
    public static final int _1W=10000;
    public static final int threadNumber=50;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        long startTime;
        long endTime;

        CountDownLatch countDownLatch1 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch2 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch3 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch4 = new CountDownLatch(threadNumber);

        startTime = System.currentTimeMillis();
        for (int i = 1; i <= threadNumber; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.clickByLongAccumulator();
                    }
                } finally {
                    countDownLatch1.countDown();
                }
            }).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("----constTime:"+(endTime-startTime)+" 毫秒\tclickByLongAccumulator:"+clickNumber.longAccumulator.get());
    }
}

/**
 * ----constTime:3921 毫秒	clickBySynchronized:50000000
 * ----constTime:730 毫秒	clickByAtomicLong:50000000
 * ----constTime:78 毫秒	clickByLongAdder:50000000
 * ----constTime:109 毫秒	clickByLongAccumulator:50000000
 */
class ClickNumber
{
    int number = 0;
    public synchronized void clickBySynchronized(){
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);
    public void clickByAtomicLong(){
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();
    public void clickByLongAdder(){
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator((x,y)->x+y,0);
    public void clickByLongAccumulator(){
        longAccumulator.accumulate(1);
    }
}
