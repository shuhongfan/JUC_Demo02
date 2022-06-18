package com.shf.MyJUC;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

public class LongAdderAPIDemo {
    public static void main(String[] args) {
        /**
         * 只能用来计算加法，且从0开始
         */
        LongAdder longAdder = new LongAdder();

        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println(longAdder.sum());

        /**
         * 提供自定函数操作
         */
        LongAccumulator longAccumulator = new LongAccumulator((x,y)->x+y,0);
        longAccumulator.accumulate(1); // 1
        longAccumulator.accumulate(3); // 4
        System.out.println(longAccumulator.get());


        LongAccumulator longAccumulator1 = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        }, 0);


    }
}
