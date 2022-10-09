package com.shf.myjuc2.syncUp;

import org.openjdk.jol.info.ClassLayout;

/**
 * synchronized用的锁是存在Java对象头里的Mark Word中
 * 锁升级功能主要依赖MarkWord中锁标志位的释放偏向锁标志位
 *
 * 无锁：001,
 * 偏向锁：101, MarkWord存储的是偏向的线程Id。
 *        当一段同步代码移植被同一个线程多次访问，由于只有一个线程那么该线程在后序访问时便会自动获得锁。
 *
 *        偏向锁的撤销：
 *           偏向锁使用一种等到竞争才出现释放锁的机制，只有当其他线程竞争锁时，持有偏向锁的原来线程才会被撤销。
 *           撤销需要等待全局安全点（该时间点没有字节码正在执行），同时检车持有偏向锁的线程是否还在执行
 *
 * 轻量锁：00， MarkWord存储的是指向线程栈中LockRecord的指针
 *      自适应自旋：自适应意味着自旋的次数是固定不变的，而是根据同一个锁上一次自旋的时间，拥有锁线程的状态来决定
 *      轻量锁争夺轻量级锁失败时，自旋尝试抢占锁
 *      轻量锁每次退出同步块都需要释放锁，而偏向锁是在竞争发生时才释放锁
 *
 * 重量锁10： MarkWord存储的是指向堆中的monitor对象的指针
 *      在无锁状态下，Mark Word中可以存储对象的identity hash code值。当对象的hashCode()方法第一次 被调用时，JVM会生成对应的identity hash code值并将该值存储到Mark Word中。
 *
 *      对于偏向锁，在线程获取偏向锁时，会用Thread ID和epoch值覆盖identity hash code所在的位置。如果-个对象的hashCode()方法已经被调用过一次之后，这个对象不能被设置偏向锁。
 *      因为如果可以的化，那Mark Word中的identity hash code必然会被偏向线程id给覆盖，这就会造成同一个对象前后两次调用hashCode()方法得到的结果不--致。
 *
 *      升级为轻量级锁时，JVM会在当前线程的栈帧中创建个锁记录(LockRecord)空间，用于存储锁对象的MarkWord拷贝，该拷贝中可以包含identity hash code，
 *      所以轻量级锁可以和identity hash code共存，哈希码和GC年龄自然保存在此，释放锁后会将这些信息写回到对象头。
 *
 *      升级为重量级锁后，Mark Word保存的重量级锁指针，代表重量级锁的ObjectMonitor类里有字段记录非加锁状态下的Mark Word,锁释放后也会将信息写回到对象头。
 *
 *      当一个对象已经计算过identity hashcode，它就无法进入偏向锁状态，跳过偏向锁，直接升级轻量级锁
 *      偏向锁过程中遇到一致性哈希计算请求，立马撤销偏向模式，膨胀为重量级锁
 *
 *
 *
 *  锁的优缺点的对比
 *    锁         优点      缺点          适用场景
 *    偏向锁101     加锁和解锁不需要额外的消耗，和执行非同步方法相比仅存在纳秒级的差距
 *              如果线程间存在锁竞争，会带来额外的锁撤销的消耗
 *              适用于只有一个线程访问的同步块的场景
 *
 *    轻量级锁00    竞争的线程不会阻塞，提高了程序的响应速度
 *              如果始终得不到锁竞争的线程，使用自旋会消耗CPU
 *              追求响应时间，同步块执行速度非常快
 *
 *    重量级锁10    线程竞争不使用自旋，不会消耗CPU
 *              线程阻塞，响应时间缓慢
 *              最求吞吐量，同步块执行速度较长
 *
 *  synchronized 锁升级过程总结：一句话，就是先自旋，不行再阻塞，
 *              实际上就是把之前的悲观锁（重量级锁）变成再一定条件下使用偏向锁以及使用轻量锁（自旋锁CAS）的形式
 *
 *  Synchronized再修饰方法代码块再字节码上实现方式有很大差异，但是内部实现还是基于对象头的MarkWord来实现的。
 *
 *  JDK1.6之前synchronized使用的是重量级锁，JDK1.6之后进行优化，拥有了无锁---》偏向锁---》轻量锁---》重量锁的锁升级过程，而不是无论什么情况都使用重量级锁
 *
 *  小总结：
 *      没有锁：自由自在
 *      偏向锁：唯我独尊
 *      轻量锁：楚汉争霸
 *      重量锁：群雄逐鹿
 */
public class synchronizeDemo {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println("10进制："+o.hashCode());
        System.out.println("16进制："+Integer.toHexString(o.hashCode()));
        System.out.println("2进制："+Integer.toBinaryString(o.hashCode()));

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
