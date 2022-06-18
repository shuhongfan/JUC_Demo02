package com.shf.MyJUC;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureAPI3Demo {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(()->{
            return 1;
        }).thenApply(f->{
            return f+2;
        }).thenApply(f->{
            return f+3;
        }).thenAccept(System.out::println);

        System.out.println(
                CompletableFuture.supplyAsync(()->"resultA")
                        .thenRun(()->{})
                        .join()
        );

        System.out.println(
                CompletableFuture.supplyAsync(()->"resultB")
                        .thenAccept((r)->{
                            System.out.println(r);
                        })
                        .join()
        );

        System.out.println(
                CompletableFuture.supplyAsync(()->"resultC")
                        .thenApply((r)-> r+"---resultC")
                        .join()
        );
    }
}
