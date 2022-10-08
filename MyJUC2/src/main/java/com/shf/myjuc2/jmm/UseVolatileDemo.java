package com.shf.myjuc2.jmm;

/**
 * 低开销的读，写锁策略
 *
 * 当读远多于写，结合使用内部锁和volatile遍历来减少同步的开销
 * 理由：利用volatile保证读取操作的可见性，利用synchronize保证复核操作的原子性
 */
public class UseVolatileDemo {
    public class Counter {
        private volatile int value;

        public int getValue() {
            return value;  // 利用Volatile保证读取操作的可见性
        }

        public synchronized int increment() {
            return value++; // 利用synchronized保证复核操作的原子性
        }
    }
}
