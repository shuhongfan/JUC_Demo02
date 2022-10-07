package com.shf.myjuc2.cf;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 1.没有传入自定义线程池，都用默认线程池ForkJoinPool
 * 2.传入了一个自定义线程池
 *      如果你执行第一个任务的时候，传入一个自定义线程池
 *      调用thenRun方法执行第二个任务的时候，则第二个任务和第一个任务是共用一个线程池
 *      调用thenRunAsync执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池
 * 3.备注
 *      有可能处理太快，系统切换原则，直接使用main线程处理
 *
 *    其他如  thenAccept 和 thenAcceptAsync，thenApply 和 thenApplyAsync 等，它们之间的区别也是同理
 */
public class CompletableFutureWithThreadPoolDemo {
    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("1号任务：" + "\t" + Thread.currentThread().getName());
            return "abcd";
        }, threadPool).thenRunAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("2号任务：" + "\t" + Thread.currentThread().getName());
        }).thenRun(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("3号任务：" + "\t" + Thread.currentThread().getName());
        }).thenRun(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("4号任务：" + "\t" + Thread.currentThread().getName());
        });

        System.out.println(completableFuture.get(2L,TimeUnit.SECONDS));

        threadPool.shutdown();
    }
}
