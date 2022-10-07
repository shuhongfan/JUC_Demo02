package com.shf.myjuc2.cf;

import lombok.SneakyThrows;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureAPIDemo {
    @SneakyThrows
    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<String>(()->{
            System.out.println(Thread.currentThread().getName()+"\t ----come in");

            TimeUnit.SECONDS.sleep(5);

            return "task over";
        });

        Thread t1 = new Thread(futureTask, "t1");
        t1.start();

        System.out.println(Thread.currentThread().getName()+"\t ------忙其他任务了");

        // 1.get会阻塞  不见不散，不管你是否计算完成，容易程序阻塞，一般建议放到程序后面
//        System.out.println(futureTask.get());

        // 只愿意等待3秒，过时不候
//        System.out.println(futureTask.get(3,TimeUnit.SECONDS));

        // 2.isDone 轮询
        // 轮询方式会耗费无畏的CPU资源，而且也不见得能及时获得计算结果
        // 如果想要异步获取结果，通常都会以轮询的方式去获取结果，尽量不要阻塞
        while (true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            } else {
                TimeUnit.MILLISECONDS.sleep(500);

                System.out.println("正在处理中...");
            }
        }
    }
}
