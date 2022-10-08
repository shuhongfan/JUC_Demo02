package com.shf.myjuc2.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderAPIDemo {
    public static void main(String[] args) {
//        只能从0开始累加
        LongAdder longAdder = new LongAdder();

        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println(longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(3);
        System.out.println(longAccumulator.get());
    }
}
