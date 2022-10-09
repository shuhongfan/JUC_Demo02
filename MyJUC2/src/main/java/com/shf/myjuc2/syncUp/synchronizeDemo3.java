package com.shf.myjuc2.syncUp;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 当一个对象已经计算过identity hashcode，它就无法进入偏向锁状态，跳过偏向锁，直接升级轻量级锁
 */
public class synchronizeDemo3 {
    @SneakyThrows
    public static void main(String[] args) {
        TimeUnit.SECONDS.sleep(5);

        Object o = new Object();
        System.out.println("本应该是偏向锁");
        System.out.println(ClassLayout.parseInstance(o).toPrintable()); // 101

        o.hashCode();

        synchronized (o) {
            System.out.println("本应该是偏向锁，但是由于计算过一致性哈希，会直接升级为轻量级锁");
            System.out.println(ClassLayout.parseInstance(o).toPrintable()); // 00
        }
    }
}
