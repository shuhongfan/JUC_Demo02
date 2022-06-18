package com.shf.MyJUC;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ------- come in");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task over";
        });

        Thread t1 = new Thread(futureTask, "t1");
        t1.start();

//        不见不散，非要等到结果才会离开  .get()
//        System.out.println(futureTask.get());

//        超时抛出异常  java.util.concurrent.TimeoutException
//        System.out.println(futureTask.get(3, TimeUnit.SECONDS));

        System.out.println(Thread.currentThread().getName() + "\t ------- main in");

        while (true){ // 轮旋
            if (futureTask.isDone()){  // 状态判断
                System.out.println(futureTask.get());
                break;
            } else {
//                暂停毫秒
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("正在处理中，请稍候！！！");
            }
        }
    }
}

/**
 * 1.get容易导致阻塞，一般建议放到程序后面，一旦调用不见不散，非要等到程序结果才会离开，不断你是否计算完成，容易程序阻塞
 * 2.假如我不愿意等待很长时间，我希望过时不候，可以自动离开
 *
 */
