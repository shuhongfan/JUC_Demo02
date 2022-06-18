package com.shf.MyJUC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo02 {
    public static void main(String[] args) {
        MyData myData = new MyData();
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {
            for (int i = 0; i < 10; i++) {
                threadPool.submit(()->{
                    Integer beforeInt = myData.threadLocal.get();
                    myData.add();
                    Integer afterInt = myData.threadLocal.get();
                    System.out.println(Thread.currentThread().getName()+"\tbeforeInt:"+beforeInt+"\tafterInt:"+afterInt);
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            myData.threadLocal.remove();
            threadPool.shutdown();
        }
    }
}

class MyData
{
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public void add(){
        threadLocal.set(1+ threadLocal.get());
    }
}
