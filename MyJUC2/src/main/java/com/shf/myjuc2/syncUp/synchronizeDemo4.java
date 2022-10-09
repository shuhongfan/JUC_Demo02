package com.shf.myjuc2.syncUp;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 偏向锁过程中遇到一致性哈希计算请求，立马撤销偏向模式，膨胀为重量级锁
 */
public class synchronizeDemo4 {
    @SneakyThrows
    public static void main(String[] args) {
        TimeUnit.SECONDS.sleep(5);

        Object o = new Object();

        synchronized (o) {
            o.hashCode();

            System.out.println("偏向锁过程中遇到一致性哈希计算请求，立马撤销偏向锁模式，膨胀为重量级锁");
            System.out.println(ClassLayout.parseInstance(o).toPrintable()); // 10
        }
    }
}
