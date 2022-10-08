package com.shf.myjuc2.atomic;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder为什么这么快？
 *
 * LongAdder的基本思路就是分散热点，将value值分 散到一-个Cell数组中，不同线程会命中到数组的不同槽中，
 * 各个线程只对自己槽中的那个值进行CAS操作，这样热点就被分散了，冲突的概率就小很多。如果要获取真正的ong值，
 * 只要将各个槽中的变量值累加返回。
 *
 * sum()会将所有Cel数组中的value和base累加作为返回值，核心的思想就是将之前AtomicLong-个value的更新压力分散到多个value中去，
 * 从而降级更新热点。
 *
 *
 * LongAdder在无竞争的情况，跟AtomicLong一样， 对同一个base进行操作， 当出现竞争关系时则是采用化整为零分散热点的做法，用空间换时间
 * ，用一个数组cells， 将一个value拆分进这个数组cells. 多个线程需要同时对value进行操作时候，可以对线程id进行hash得到hash值， 再根据
 * hash值映射到这个数组cells的某个下标，再对该下标所对应的值进行自增操作。当所有线程操作完毕，将数组cells的所有值和base都加起来作为
 * 最终结果。
 *
 *
 * LongAdder最初无竞争时只更新base，
 * 如果更新base失败后，首次新建一个Cell数组
 * 当多个线程竞争同一个Cell比较激烈时，可能要对cell进行扩容
 *
 * 1. 如果Cells为空，尝试更新base字段，成功则退出
 * 2. 如果Cells表为空，CAS更新base字段失败，出现竞争，uncontented为true，调用longAccumulate
 * 3. 如果Cells表非空，但当前线程映射的槽为空，uncontentded为true，调用longAccumulate
 * 4. 如果Cells表非空，且线程映射的槽非空，CAS更新Cell的值，成功则返回，否则，uncontented设为false，调用LongAccumulate
 *
 * 总结
 * AtomicLong
 *      线程安全，可允许一些性能损耗，要求高精度时使用
 *      保证精度，性能代价
 *      AtomicLong是多线程针对单个热点值value进行原子操作
 *   原理： CAS+自旋，incrementAndGet
 *   场景：低并发下的全局计算，AtomicLong能保证并发情况下计数的准确性，其内部通过CAS来解决并发安全问题
 *   缺陷： 高并发后性能急剧下降
 *          why？ AtomicLong的自旋会成为瓶颈（N个线程CAS操作会修改线程的值，每次只有一个成功，其他N-1失败，失败的不停自旋直到成功，这样大量失败自旋的情况，一下子CPU就打高了）
 *
 * LongAdder
 *      当需要在高并发下有较好的性能表现，且对值的精确度要求不高时，可以使用
 *      保证性能，精度代价
 *      LongAdder是每个线程拥有自己的槽，各个线程一般只对自己槽中的那个值就行CAS操作
 *   原理： CAS+Base+Cell数组分散，空间换时间分散了热点数据
 *   场景： 高并发下全局计算
 *   缺陷： sum求和后还有计算线程修改结果的话，最后结果不够精确
 *
 */
public class AccumulatorCompareDemo {

    public static final int _1W = 10000;
    public static final int threadNumber = 50;

    @SneakyThrows
    public static void main(String[] args) {
        ClickNumber clickNumber = new ClickNumber();
        long startTime;
        long endTime;

        CountDownLatch countDownLatch1 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch2 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch3 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch4 = new CountDownLatch(threadNumber);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.clickBySynchornized();
                    }
                } finally {
                    countDownLatch1.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("----- costTime:"+(endTime-startTime)+"毫秒"+"\t clickBySynchornized:"+clickNumber.number);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.clickByAtomicLong();
                    }
                } finally {
                    countDownLatch2.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("----- costTime:"+(endTime-startTime)+"毫秒"+"\t clickByAtomicLong:"+clickNumber.atomicLong.get());


        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.clickByLongAdder();
                    }
                } finally {
                    countDownLatch3.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("----- costTime:"+(endTime-startTime)+"毫秒"+"\t clickByLongAdder:"+clickNumber.longAdder.sum());


        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.clickByLongAccumulator();
                    }
                } finally {
                    countDownLatch4.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println("----- costTime:"+(endTime-startTime)+"毫秒"+"\t clickByLongAccumulator:"+clickNumber.longAccumulator.get());


    }
}

class ClickNumber {
    int number = 0;

    public synchronized void clickBySynchornized() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);

    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();

    public void clickByLongAdder() {
        longAdder.increment();
    }


    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }

}
