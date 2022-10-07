package com.shf.myjuc2.cf;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 目的：异步多线程任务执行并且返回有结果
 * 三个特点：多线程/有返回值/异步任务  Runnable接口, Future
 *
 * Future接口可以为主线程开启一个分支任务，专门为主线程处理耗时和费力的复杂业务，提供异步并行计算的功能
 * 如果主线程需要指向一个很耗时的计算任务，我们就可以通过future把这个任务放到异步线程中执行。
 * 主线程继续处理其他任务或者先行结束，再通过Future获取计算结果。
 *
 * Future接口常用实现类FutureTask实现异步任务
 */
public class CompletableFutureDemo {
    @SneakyThrows
    public static void main(String[] args) {

        FutureTask<String> futureTask = new FutureTask<>(new MyThread());

        Thread t1 = new Thread(futureTask, "t1");
        t1.start();

//        获取异步线程的结果
        System.out.println(futureTask.get());
    }
}


class MyThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("-----come in call");
        return "hello callable";
    }
}
