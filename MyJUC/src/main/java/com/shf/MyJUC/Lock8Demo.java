package com.shf.MyJUC;

import java.util.concurrent.TimeUnit;

/**
 *
 */
class Phone { // 资源类
    public static synchronized  void sendEmail() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-----sendEmail");
    }

    public static synchronized void sendSMS(){
        System.out.println("-----sendSMS");
    }

    public void hello(){
        System.out.println("----hello");
    }
}

public class Lock8Demo
{
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(()->{
            phone.sendEmail();
        },"a").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            phone.sendSMS();
//            phone.hello();
//            phone2.sendSMS();
        },"b").start();
    }
}
