package com.shf.myjuc2.jvm;

/**
 * 在HotSpot虚拟机里，对象在堆内存中的存储布局可以划分为三个部分：
 * 对象头： 在64位系统中，MarkWord占了8个字节，类型指针占了8个字节，一共是16个字节
 *      对象标记（mark word）:默认存储对象的HashCode、分代年龄和锁标志位等信息。
 *                      这些信息都是与对象自身定义无关的数据，所以MarkWord被设计成一个非固定的数据结构以便在极小的空间内存存储尽量多的数据。
 *                      它会根据对象的状态复用自己的存储空间，也就是说在运行期间MarkWord里存储的数据会随着锁标志位的变化而变化。
 *      类元信息（类型指针）：对象指向它的类元数据的指针，虚拟机通过这个指针来确定这个对象是哪个类的实例。
 * 实例数据
 * 对齐填充（保证8个字节的倍数）
 */
public class ObjectHeadDemo {
    public static void main(String[] args) {
        Object o = new Object();  // new一个对象，占内存多少

        System.out.println(o.hashCode()); // 这个hashCode记录在对象的什么地方

        synchronized (o) {

        }

        System.gc();
    }
}


