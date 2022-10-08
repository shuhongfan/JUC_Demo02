package com.shf.myjuc2.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS
 * compare and swap的缩写，中文翻译成比较并交换,实现并发算法时常用到的一种技术。它包含三个操作数——内存位置、预期原值及更新值。
 * 执行CAS操作的时候，将内存位置的值与预期原值比较:如果相匹配，那么处理器会自动将该位置值更新为新值，
 * 如果不匹配，处理器不做任何操作，多个线程同时执行CAS操作只有一个会成功。
 */
public class T3 {
    volatile int number = 0;

    public int getNumber() {
        return number;
    }

    public synchronized void setNumber() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public int getAtomicInteger() {
        return atomicInteger.get();
    }

    public void setAtomicInteger() {
        atomicInteger.getAndIncrement();
    }

}
