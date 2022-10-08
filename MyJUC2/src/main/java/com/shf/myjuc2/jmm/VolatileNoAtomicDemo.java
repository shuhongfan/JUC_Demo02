package com.shf.myjuc2.jmm;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

class MyNumber {
    volatile int number;

    public void addPlusPlus() {
        number++;
    }
}

public class VolatileNoAtomicDemo {
    @SneakyThrows
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                for (int j = 1; j <= 1000; j++) {
                    myNumber.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }

        TimeUnit.SECONDS.sleep(2);
        System.out.println(myNumber.number);
    }
}
