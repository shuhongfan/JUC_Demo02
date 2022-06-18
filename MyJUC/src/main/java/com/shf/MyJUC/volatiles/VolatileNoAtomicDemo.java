package com.shf.MyJUC.volatiles;

import java.util.concurrent.TimeUnit;

public class VolatileNoAtomicDemo {
    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    myNumber.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }

        TimeUnit.SECONDS.sleep(2);

        System.out.println(myNumber.number);
    }
}

class MyNumber
{
    /**
     * volatile 没有原子性
     */
    volatile int  number;

    public void addPlusPlus(){
        number++;
    }
}
