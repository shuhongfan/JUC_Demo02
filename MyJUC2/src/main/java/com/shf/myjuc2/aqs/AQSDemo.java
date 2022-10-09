package com.shf.myjuc2.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 抽象队列同步器
 * 整体就是一个抽象的FIFO队列；来完成资源获取线程的排队工作，并通过一个int类变量表示持有锁的状态
 *
 * AQS使用一个volatile的int类型的成员变量来表示同步状态，通过内置的FIFO队列来完成资源获取的排队工作将每条要去抢占资源的
 * 线程封装成一个Node节点来实现锁的分配，通过CAS完成对State值的修改。
 * AQS内部维护-一个同步队列，元素就是包装了线程的Node.
 * 同步队列中首节点是获取到锁的节点，它在释放锁的时会唤醒后继节点，后继节点获取到锁的时候，会把自己设为首节点。
 * 线程会先尝试获取锁，失败则封装成Node, CAS加入同步队列的尾部。在加入同步队列的尾部时，会判断前驱节点是否是head结点，并尝试加
 * 锁(可能前驱节点刚好释放锁)，否则线程进入阻塞等待。
 *
 * CLH: Craig、 Landin and Hagersten队列，是个单问链表，AQS中的队列是CLH变体的虚拟双向队列(FIFO)
 *
 * 对比公平锁和非公平锁的tryAcquire()方法的实现代码，其实差别就在于非公平锁获取锁时比公平锁中少了-一个判断!hasQueuedPredecessors()
 * hasQueuedPredecessors()中判断了是否需要排队，导致公平锁和非公平锁的差异如下:
 *
 * 公平锁:公平锁讲究先来先到，线程在获取锁时，如果这个锁的等待队列中已经有线程在等待，那么当前线程就会进入等待队列中;
 * 非公平锁:不管是否有等待队列，如果可以获取锁，则立刻占有锁对象。也就是说队列的第一一个排队线程苏醒后，不一-定 就是排头的这个线程获
 * 得锁，它还是需要参加竞争锁(存在线程竞争的情况下)，后来的线程可能不讲武德插队夺锁了。
 *
 * 在创建完公平锁/非公平锁后,调用lock方法会进行加锁，最终都会调用到acquire方法。
 *
 */
public class AQSDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        // A B C三个顾客， 去银行办理业务，A先到，此时窗口空无-人，他优先获得办理窗口的机会，办理业务。
        // A耗时严重，估计长期占有窗口
        new Thread(()->{
            lock.lock();
            try {
                System.out.println("-------- come in A");
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        },"A").start();

        //B尼第2个顾客，B- 看到受理窗口被A占用，只能去候客区等待， 进入AQS以(列， 等待A办理完成，尝试去抢占受理窗口。
        new Thread(()->{
            lock.lock();
            try {
                System.out.println("-------- come in B");
            } finally {
                lock.unlock();
            }
        },"B").start();

        //C足第3个顾客，c-看到受理窗A古用，只能去候客区等待，进入AQS以列，等待A办理完成，尝试去抢占受理窗口， 前面足B顾客，FIFO
        new Thread(()->{
            lock.lock();
            try {
                System.out.println("-------- come in B");
            } finally {
                lock.unlock();
            }
        },"C").start();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("-------- come in B");
            } finally {
                lock.unlock();
            }
        },"D").start();


    }
}
