package com.shf.myjuc2.atomic;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {
    @SneakyThrows
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
//                        bankAccount.add();
                        bankAccount.transMoney(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t result=" + bankAccount.money);
    }
}

class BankAccount {
    String bankName = "CCB";

//    更新的对象属性必须使用public volatile 修饰符
    public volatile int money = 0; // 钱数

    public void add() {
        money++;
    }

    //    因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须使用静态方法newUpdater创建一个更新器，
    //    并且需要设置更新的类和属性
    AtomicIntegerFieldUpdater<BankAccount> fieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"money");

    //    不加Synchronize，保证高性能原子性，局部微创小手术
    public void transMoney(BankAccount bankAccount) {
        fieldUpdater.getAndIncrement(bankAccount);
    }

}