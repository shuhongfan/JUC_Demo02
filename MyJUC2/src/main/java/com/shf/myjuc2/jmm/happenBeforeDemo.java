package com.shf.myjuc2.jmm;

/**
 * 在JMM中，如果一个操作执行的结果需要对另一个操作可见性或者代码重排序，那么这两个操作之间必须存在happen-before（先行发生）原则，逻辑上的先后关系
 *
 * happens-before总原则：
 *   如果一个操作happens-before另一个操作，那么第一个操作的执行结果将对第二个操作可见，而且第一个操作的执行顺序排在第二个操作之前
 *   两个操作之间存在happens-before关系，并不意味着一定要按照happens-before原则制定的顺序来执行。
 *   如果重排序之后的执行结果与按照happens-before关系来执行的结果一致，那么这种重排序并不非法。
 *
 * happens-before之8条：
 *      1.次序规则：一个线程内，按照代码顺序，写在前面的操作先行发生于现在后面的操作
 *      2.锁定规则：一个unLock操作先行发生于后面对于同一个锁的lock操作
 *      3.volatile变量规则：前面的写对后面的读是可见的
 *      4.传递规则： 如果操作A先行于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C
 *      5.线程启动规则：Thread对象的start方法先行于此线程的每一个动作
 *      6.线程中断规则：对线程interrupt方法的调用先行于被中断线程的代码检测到中断事件的发生
 *      7.线程终止规则：线程中所有操作都先行发生于对此线程的终止检测
 *      8.对象终结规则：对象没有初始化完成之前，是不能调用finalized方法的
 */
public class happenBeforeDemo {
}
