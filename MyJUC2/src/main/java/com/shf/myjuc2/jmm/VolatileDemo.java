package com.shf.myjuc2.jmm;

/**
 *
 * 当写一个volatile变量时，JMM会把该线程所对应的本地内存中的共享变量值立即刷新回主内存中
 * 当读一个volatile变量是，JMM会把该线程所对应的本地内存设置为无效，重新回到主内存中读取最新共享变量
 * 索引volatile的写内存语义是直接刷新到主内存中，读的内存语义是直接冲主内存中读取
 *
 * 内存屏障（也称内存栅栏，屏障指令等，是一类同步屏障指令，是CPU或编译器在对内存随机访问的操作中的一个同步点，
 * 使得此点之前的所有读写操作都执行后才可以开始执行此点之后的操作），避免代码重排序。内存屏障其实就是一种JVM指令，
 * Java内存模型的重排规则会要求Java编译器在生成JVM指令时插入特定的内存屏障指令，通过这些内存屏障指令，
 * volatle实现了Java内存模型中的可见性和有序性(禁重排)，但volatile无法保证原子性。
 *
 * 内存屏障之前的所有写操作都要写回主内存
 * 内存屏障之后的所有读操作都能获得内存屏障之前的所有写操作的最新结果（实现了可见性）
 *
 * 因此重排序时，不允许把内存屏障之后的指令重排序到内存屏障之前。
 * 一句话：对一个volatile变量的写，先行发生于任意后序volatile变量的读，也叫写后读
 *
 * 内存屏障分类：
 *      读屏障
 *      写屏障
 *
 *  四大屏障分别是什么意思
 *      LoadLoad    保证load1的读取操作在load2后序读取操作之前执行
 *      StoreStore  在store2及其后的写操作执行之前，保证store1的写操作已经刷新到主内存
 *      LoadStore   在store2及其后的写操作执行前，保证load1的读操作已读取结束
 *      StoreLoad   保证store1的写操作已经刷新到主内存之后，load2及其后的读操作才能执行
 *
 *  编译器优化的重排序:编译器在不改变单线程串行语义的前提下，可以重新调整指令的执行顺序
 *  指令级并行的重排序:处理器使用指令级并行技术来讲多条指令重叠执行，若不存在数据依赖性，
 *  处理器可以改变语句对应机器指令的执行顺序内存系统的重排序:由于处理器使用缓存和读/写缓冲区，
 *  这使得加载和存储操作看上去可能是乱序执行
 *
 * 当第一个操作为volatle读时，不论第二个操作是什么，都不能重排序。这个操作保证了volatile读之后的操作不会被重排到volatile读之前。
 * 当第二个操作为volatile写时，不论第一个操作是什么，都不能重排序。这个操作保证了volatile写之前的操作不会被重排到volatile写之后。
 * 当第一个操作为volatile写时，第二个操作为volatile读时，不能重排。
 *
 *
 * 总结：
 * volatile写之前的操作，都禁止重排序到volatile之后
 * volatile读之后的操作，都禁止重排序到volatile之前
 * volatile写之后volatile读，禁止重排序
 */
public class VolatileDemo {
}