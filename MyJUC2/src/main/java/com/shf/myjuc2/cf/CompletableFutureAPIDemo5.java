package com.shf.myjuc2.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture
 * 两个结果进行合并 thenCombine
 *
 * 两个CompletionStage任务都完成后，最终能把两个任务的结果一起交给thenCombine来处理
 * 先完成的先等着，等待其他分支任务
 */
public class CompletableFutureAPIDemo5 {
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ------ 启动");

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return 10;
        });

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t----启动");

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return 20;
        });

        CompletableFuture<Integer> result = completableFuture1.thenCombine(completableFuture1, (x, y) -> {
            System.out.println("-------开始两个结果合并");
            return x + y;
        });
        System.out.println(result.join());
    }
}
