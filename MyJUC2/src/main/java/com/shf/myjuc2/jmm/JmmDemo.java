package com.shf.myjuc2.jmm;

/**
 * JMM 屏蔽掉各种硬件和操作系统的内存访问差异，以实现Java程序在各种平台下都能达到一致的内存访问效果。
 *
 * JMM本身是一种抽象的概念并不是真是存在的它仅仅描述的是一组约定或规范，通过这组规范定义了程序中（尤其是多线程）各个变量的读写访问方式
 * 并决定一个线程对共享变量的写入并对另一个线程可见，关键技术点都是围绕多线程的原子性、可见性和有序性展开的。
 *
 * 原则：
 * JMM的关键技术点都是围绕多线程的原子性、可见性和有序性展开的
 *
 * 能干嘛？
 * 1. 通过JMM来实现线程和主内存之间的抽象关系
 * 2. 屏蔽各个硬件平台和操作系统的内存访问差异以实现Java程序在各种平台下都能达到一致的内存访问效果。
 *
 *
 * JMM规范下，三大特性：
 *  可见性：当一个线程修改了某一个共享变量的值，其他线程是否能够立即知道该变更，JMM规定了所有的变量都存储在主内存中
 *          系统主内存共享变量数据修改被写入的时机是不确定的，多线程并发下很可能出现脏读，所以每个线程都有自己的工作内存，线程自己的
 *          工作内存中保存了该线程使用到的变量的主内存副本拷贝，线程对变量的所有操作（读取、赋值等）都必须在线程自己的工作内存中进行，
 *          而不是写入主内存中的变量。不同线程之间也无法直接访问对方工作内存中的变量，线程间变量值的传递均需要通过主内存来完成。
 *  原子性
 *  有序性：对于一个线程的执行代码而言，我们总是习惯认为代码的执行总是从上到下，有序执行。但为了提升性能，编译器和处理器通常会对指令序列
 *          进行重新排序。Java规范规定JVM线程内部维持顺序化语义，即只要程序的最终结果与他顺序化执行的结果相等，那么指令的执行顺序可以
 *          与代码顺序不一致，此过程叫指令的重排序。
 *
 *  JMM定义了线程和主内存之间的抽象关系
 *  1. 线程之间的共享变量存储主内存中（从硬件角度来说就是内存条）
 *  2.每个线程都有一个私有的本地工作内存，本地工作内存存储了该线程用来读/写共享变量的副本（从硬件角度来说就是CPU缓存）
 */
public class JmmDemo {
}