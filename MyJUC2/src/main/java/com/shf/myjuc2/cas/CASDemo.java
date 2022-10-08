package com.shf.myjuc2.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * public final boolean compareAndSet(int expect, int update) {
 *         return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
 *     }
 *
 * public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);
 *
 * 上面三个方法都是类似的，主要对4个参数做一下说明。
 * var1:表示要操作的对象
 * var2:表示要操作对象中属性地址的偏移量
 * var4:表示需要修改数据的期望的值
 * var5/var6:表示需要修改为的新值
 *
 * CAS  并发原语体现在JAVA语言中就是sun.misc.Unsafe类中的各个方法。
 * 调用UnSafe类中的CAS方法，JVM会帮我们实现出CAS汇编指令。这是一种完全依赖于硬件的功能，通过它实现了原子操作。
 * 再次强调，由于CAS是一种系统原语，原语属于操作系统用语范畴，是由若干条指令组成的，用于完成某个功能的一个过程，
 * 并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说CAS是一条CPU的原子指令，不会造成所谓的数据不一致问题。
 *
 * 假设线程A和线程B两个线程同时执行getAndAddInt操作（分别跑在不同CPU上) :
 * 1 AtomicInteger里面的value原始值为3，即主内存中AtomicInteger的value为3，根据JMM模型,线程A和线程B各自持有一份值为3的value的副本分别到各自的工作内存。
 * 2线程A通过getIntVolatile(var1, var2)拿到value值3，这时线程A被挂起。
 * 3线程B也通过getIntVolatile(var1, var2)方法获取到value值3，此时刚好线程B没有被挂起并执行compareAndSwaplnt方法比较内存值也为3，成功修改内存值为4，线程B打完收工，一切OK。
 * 4这时线程A恢复，执行compareAndSwapInt方法比较，发现自己手里的值数字3和主内存的值数字4不一致，说明该值已经被其它线程抢先一步修改过了，那A线程本次修改失败，只能重新读取重新来一遍了。
 * 5线程A重新获取value值，因为变量value被volatile修饰，所以其它线程对它的修改，线程A总是能够看到，线程A继续执行compareAndSwaplnt进行比较替换，直到成功。
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5,2022)+"\t"+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5,2022)+"\t"+atomicInteger.get());
    }
}
