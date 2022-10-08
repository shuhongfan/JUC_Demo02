package com.shf.myjuc2.ThreadLocal;

/**
 * 为什么源代码用弱引用?
 * 当function01 方法执行完毕后，栈帧销毁强引用tl也就没有了。但此时线程的ThreadLocalMap里某个entry的key引用还指向这个对象
 * 若这个key引用是强引用，就会导致key指向的ThreadL ocal对象及v指向的对象不能被gc回收，造成内存泄漏;
 * 若这个key引用是弱引用就大概率会减少内存泄漏的问题(还有- -个key为null的雷，第2个坑后面讲)。使用弱引用，
 * 就可以使ThreadLocal对象在方法执行完毕后顺利被回收且Entry的key引用指向为null.
 *
 *
 * 1当我们为threadLocal变量赋值，实际上就是当前的Entry(threadL ocal实例为key，值为value)往这 个threadLocalMap中存放。Entry中的key是弱
 * 引用，当threadL ocal外部强引用被置为nl(l=nl),那么系统GC的时候，根据可达性分析，这个threadL ocal实例就没有任何一条 链路能够引用到
 * 它，这个ThreadLocal势必会被回收。这样一来， ThreadLocalMap中 就会出现key为null的Entry，就没有办法访问这些key为null的Entry的value,
 * 如果当前线程再迟迟不结束的话，这些key为null的Entry的value就会一直存在 - .条强引用链: Thread Ref -> Thread -> Threal ocalMap -> Entry ->
 * value永远无法回收，造成内存泄漏。
 * 2当然，如果当前thread运行结束，threadLocal, threadLocalMap,Entry没 有引用链可达，在垃圾回收的时候都会被系统进行回收。
 * 3但在实际使用中我们有时候会用线程池去维护我们的线程，比如在Executors.newFixedThreadPool()时创建线程的时候，为了复用线程是不会
 * 结束的，所以threadLoca内 存泄漏就值得我们小心
 *
 *
 * ThreadLocal并不解决线程间共享数据的问题
 * ThreadLocal适用于变量在线程间隔离且在方法间共享的场景
 * ThreadLocal通过隐式的在不同线程内创建独立实例副本避免了实例线程安全的问题
 * 每个线程持有一个只属于自己的专属Map并维护了ThreadLocal对象与具体实例的映射,
 * 该Map由于只被持有它的线程访问，故不存在线程安全以及锁的问题
 * ThreadLocalMap的Entry对Threadlocal的引用为弱引用，避免了ThreadLocal对象无法被回收的问题
 * 都会通过expungeStaleEntry，cleanSomeSlots,replaceStaleEntry这 三个方法回收键为null的Entry
 * 对象的值(即为具体实例)以及Entry对象本身从而防止内存泄漏，属于安全加固的方法
 * 群雄逐鹿起纷争，人各一份天下安
 */
public class T1 {
    volatile boolean flag;

    /**
     * line1新建了一个ThreadLocal对象，t1是强引用指向这个对象;
     * line2调用set()方法后新建-一个Entry， 通过源码可知Entry对象里的k是弱引用指向这个对象。
     * @param args
     */
    public static void main(String[] args) {
        ThreadLocal<String> t1 = new ThreadLocal<>();
        t1.set("xxx");
        t1.get();
    }
}
