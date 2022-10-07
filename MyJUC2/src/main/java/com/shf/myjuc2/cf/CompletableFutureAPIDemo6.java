package com.shf.myjuc2.cf;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureAPIDemo6 {
    @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---- come in 1");
            return 10;
        });

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---- come in 2");
            return 20;
        });

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ----- come in 4");
            return 30;
        });

        CompletableFuture<Integer> completableFuture3 = completableFuture.thenCombine(completableFuture1, (x, y) -> {
            System.out.println(Thread.currentThread().getName() + "\t ---- come in 3");
            return x + y;
        });

        CompletableFuture<Integer> completableFuture4 = completableFuture3.thenCombine(completableFuture2, (a, b) -> {
            System.out.println(Thread.currentThread().getName() + "\t ---- come in 5");
            return a + b;
        });

        System.out.println("-------- 主线程结束，end");
        System.out.println(completableFuture4.get());
    }
}
