package com.shf.myjuc2.cf;

import java.util.concurrent.CompletableFuture;

/**
 * 3.CompletableFuture 对计算结果进行消费
 * 接受任务的处理结果，并消费处理，无返回结果
 *
 *  thenRun  任务A执行完成执行B，并且B不需要A的结果
 *  thenAccept  任务A执行完成B，B需要A的结果，但是任务B无返回值
 *  ThenApply  任务A执行完成B，B需要A的结果，同时任务B有返回值
 */
public class CompletableFutureAPIDemo3 {
    public static void main(String[] args) {
//        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
//            return 1;
//        }).thenApply(f -> {
//            return f + 2;
//        }).thenApply(f -> {
//            return f + 3;
//        }).thenAccept(System.out::println);

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {}).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(System.out::println).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(r -> r+"resultB").join());
    }
}
