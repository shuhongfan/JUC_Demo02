package com.shf.MyJUC;

import java.util.concurrent.*;

public class CompletableFutureUseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "-----come in");
            int result = ThreadLocalRandom.current().nextInt(10);

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (result>2){
                int i=10/0;
            }
            System.out.println("------5秒钟后出结果：" + result);
            return result;
        },threadPool).whenComplete((v,e)->{
            if (e==null){
                System.out.println("----- 计算完成，更新系统UpdateVal："+v);
            }
        }).exceptionally(e->{
            e.printStackTrace();
            System.out.println("异常情况："+e.getCause()+"\t"+e.getMessage());
            return null;
        });
//        System.out.println(completableFuture.get());

        System.out.println(Thread.currentThread().getName() + "-----main in");

    }

    public static void future1(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "-----come in");
            int result = ThreadLocalRandom.current().nextInt(10);

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("------5秒钟后出结果：" + result);
            return result;
        });

        System.out.println(completableFuture.get());

        System.out.println(Thread.currentThread().getName() + "-----main in");
    }
}
