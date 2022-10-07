package com.shf.myjuc2.cf;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 1. CompletableFuture 获得结果和触发计算
 */
public class CompletableFutureAPIDemo {
    @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "abc";
        });

        /**
         * 获取结果
         */
//        阻塞，异常
//        System.out.println(completableFuture.get());

//        过时不候，异常
//        System.out.println(completableFuture.get(2L, TimeUnit.SECONDS));

//        阻塞，无异常
//        System.out.println(completableFuture.join());

//        没有计算完成的情况下，给我一个代替结果
//        立即获取结果不阻塞。计算完，返回计算完成后的结果，没算完，返回设定的valueIfAbsent值
//        System.out.println(completableFuture.getNow("xxx"));

        TimeUnit.SECONDS.sleep(1);
        //主动触发计算  是否打断get方法立即返回括号值
        System.out.println(completableFuture.complete("completeValue")+"\t"+completableFuture.join());
    }
}
