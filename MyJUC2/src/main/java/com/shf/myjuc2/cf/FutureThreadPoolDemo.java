package com.shf.myjuc2.cf;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**.
 * 3个任务，目前开启多个异步任务线程来处理，请问耗时多少？
 *
 * future+线程池异步多线程任务配合，能显著提高程序的执行效率
 */
public class FutureThreadPoolDemo {
    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        long startTime = System.currentTimeMillis();

        FutureTask<String> futureTask1 = new FutureTask<String>(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return "task1 over";
        });
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<String>(()->{
            TimeUnit.MILLISECONDS.sleep(300);
            return "task2 over";
        });
        threadPool.submit(futureTask2);

        FutureTask<String> futureTask3 = new FutureTask<String>(()->{
            TimeUnit.MILLISECONDS.sleep(700);
            return "task3 over";
        });
        threadPool.submit(futureTask3);


        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        System.out.println(futureTask3.get());


        long endTime = System.currentTimeMillis();
        System.out.println("-----costTime:"+(endTime-startTime)+" 毫秒");

        System.out.println(Thread.currentThread().getName()+"\t---end");

        threadPool.shutdown();
    }

    @SneakyThrows
    private static void m1() {
        long startTime = System.currentTimeMillis();

        TimeUnit.MILLISECONDS.sleep(500);
        TimeUnit.MILLISECONDS.sleep(500);
        TimeUnit.MILLISECONDS.sleep(500);

        long endTime = System.currentTimeMillis();

        System.out.println("------costTime:"+(endTime-startTime)+"毫秒");
        System.out.println(Thread.currentThread().getName()+"\t---end");
    }
}
