package com.shf.myjuc2.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 4. CompletableFuture 对计算速度进行选用  applyToEither
 */
public class CompletableFutureAPIDemo4 {
    public static void main(String[] args) {
        CompletableFuture<String> playA = CompletableFuture.supplyAsync(() -> {
            System.out.println("playA come in");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "playA";
        });

        CompletableFuture<String> playB = CompletableFuture.supplyAsync(() -> {
            System.out.println("playB come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "playB";
        });

        CompletableFuture<String> result = playA.applyToEither(playB, f -> {
            return f + "\t is winer";
        });

        System.out.println(Thread.currentThread().getName() + "\t -----" + result.join());
    }
}
