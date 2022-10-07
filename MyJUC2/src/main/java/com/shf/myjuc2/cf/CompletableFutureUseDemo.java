package com.shf.myjuc2.cf;

import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * 从Java8开始引入了CompletableFuture，它是Future的功能增强版，减少阻塞和轮询
 * 可以传入调用对象，当异步任务完成或发生异常时，自动调用回调对象的回调方法
 *
 * 异步任务结束时，会自动回调某个对象的方法
 * 主线程设置好回调后，不在关系异步任务的执行，异步任务之间可以顺序执行
 * 异步任务出错时，会自动回调某个对象的方法
 */
public class CompletableFutureUseDemo {
    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "----come in");
            int result = ThreadLocalRandom.current().nextInt(10);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("----------1秒钟后出结果：" + result);

            if (result > 5) {
                int i = 10 / 0;
            }

            return result;
        },threadPool).whenComplete((v,e)->{
            if (e == null) {
                System.out.println("---- 计算完成，更新系统UpdateVal：" + v);
            }
        }).exceptionally(e->{
            e.printStackTrace();
            System.out.println("异常情况：" + e.getCause() + "\t" + e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName()+"线程先去忙其他任务");

        threadPool.shutdown();
    }

    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "----come in");
            int result = ThreadLocalRandom.current().nextInt(10);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("----------1秒钟后出结果：" + result);
            return result;
        });

        System.out.println(Thread.currentThread().getName()+"线程先去忙其他任务");

        System.out.println(completableFuture.get());
    }
}
