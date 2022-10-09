package com.shf.myjuc2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1. CompletableFuture
 * 2.“锁"事儿
 * 3. JMM
 * 4. synchronized及升级优化
 * 5. CAS
 * 6. volatile
 * 7. LockSupport和线程中断
 * 8. AbstractQueuedSynchronizer
 * 9. ThreadLocal
 * 10.原子增强类Atomic
 *
 *
 * 1.悲观锁
 * 2.乐观锁
 * 3.自旋锁
 * 4.可重入锁(递归锁)
 * 5.写锁(独占锁)/读锁(共享锁)
 * 6.公平锁/非公平锁
 * 7.死锁
 * 8.偏向锁
 * 9.轻量锁
 * 10.重量锁
 * 11.邮戳(票据)锁
 */
@SpringBootApplication
public class MyJuc2Application {

    public static void main(String[] args) {
        SpringApplication.run(MyJuc2Application.class, args);
    }

}
