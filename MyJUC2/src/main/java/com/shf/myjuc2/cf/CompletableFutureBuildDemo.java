package com.shf.myjuc2.cf;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureBuildDemo {
    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // runAsync 无返回值
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//            System.out.println(Thread.currentThread().getName());
//
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        },threadPool);
//        System.out.println(completableFuture.get());


        // supplyAsync 有返回值
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "hello supplyAsync";
        });
        System.out.println(completableFuture.get());

        threadPool.shutdown();
    }
}
