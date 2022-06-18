package com.shf.MyJUC;

import java.util.concurrent.*;

public class CompletableFutureWithThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                    System.out.println("1号任务:\t" + Thread.currentThread().getName());
                    return "abcd";
                })
                .thenRunAsync(() -> {
                    System.out.println("2号任务\t" + Thread.currentThread().getName());
                })
                .thenRunAsync(() -> {
                    System.out.println("3号任务\t" + Thread.currentThread().getName());
                })
                .thenRunAsync(() -> {
                    System.out.println("4号任务\t" + Thread.currentThread().getName());
                });
        System.out.println(completableFuture.get(2L, TimeUnit.SECONDS));

        threadPool.shutdown();
    }
}
