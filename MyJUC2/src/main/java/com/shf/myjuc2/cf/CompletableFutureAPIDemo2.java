package com.shf.myjuc2.cf;

import java.sql.Time;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 2. CompletableFuture 对结果进行处理
 *
 * thenApply 计算结果存在依赖关系，这两个线程串行化
 *          异常相关，由于存在依赖关系（当前步错，不走下一步），当前步骤有异常的话就停止
 *
 * handle  计算结果存在依赖关系，这两个线程串行化
 *          异常相关，有异常也可以往下一步走，根据带的异常参数可以进行下一步处理
 *
 * exceptionally  -------------> try/catch
 * whenComplete / handler  ---------->  try/finally
 */
public class CompletableFutureAPIDemo2 {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("111");
            return 1;
        },threadPool).handle((f,e) -> {
            int i = 10 / 0;
            System.out.println("222");
            return f + 2;
        }).handle((f,e) -> {
            System.out.println("333");
            return f + 3;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("-------- 计算结果：" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println("exceptionally:"+e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName()+"--- 主线程先去忙其他任务");

        threadPool.shutdown();
    }
}
