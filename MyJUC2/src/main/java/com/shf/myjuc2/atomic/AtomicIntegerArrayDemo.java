package com.shf.myjuc2.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {
    public static void main(String[] args) {
//        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);
//        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5);
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }

        System.out.println();

        int tempInt = 0;
        tempInt = atomicIntegerArray.getAndSet(0, 1122);
        System.out.println(tempInt + "\t" + atomicIntegerArray.get(0));

        tempInt = atomicIntegerArray.getAndIncrement(0);
        System.out.println(tempInt + "\t" + atomicIntegerArray.get(0));

    }
}
