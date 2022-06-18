package com.shf.MyJUC;

import java.util.concurrent.*;

public class FutureThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        long startTime = System.currentTimeMillis();

        FutureTask<String> futureTask1 = new FutureTask<String>(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return "task1 over";
        });
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(300);
            return "task2 over";
        });
        threadPool.submit(futureTask2);

        FutureTask<String> futureTask3 = new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(400);
            return "task3 over";
        });
        threadPool.submit(futureTask3);

        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        System.out.println(futureTask3.get());

        threadPool.shutdown();

        long endTime = System.currentTimeMillis();

        System.out.println("----constTime:"+(endTime-startTime)+"毫秒");
    }

    public static void m1(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        TimeUnit.MILLISECONDS.sleep(500);
        TimeUnit.MILLISECONDS.sleep(300);
        TimeUnit.MILLISECONDS.sleep(300);

        long endTime = System.currentTimeMillis();

        System.out.println("----constTime:"+(endTime-startTime)+"毫秒");

        System.out.println(Thread.currentThread().getName()+"\t----end");
    }
}
