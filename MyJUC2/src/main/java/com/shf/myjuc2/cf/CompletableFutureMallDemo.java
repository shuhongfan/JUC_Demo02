package com.shf.myjuc2.cf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureMallDemo {
    @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello 1234";
        });

//        get方法必须抛出异常
        System.out.println(completableFuture.get());
        System.out.println(completableFuture.join());
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
class Student {
    private Integer id;
    private String studentName;
    private String major;
}
