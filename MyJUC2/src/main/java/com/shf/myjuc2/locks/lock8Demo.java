package com.shf.myjuc2.locks;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * 1. 标准访问有ab两个线程，请问先打印邮件还是短信
 * ---------senEmail
 * ---------sendSMS
 *
 * 2.sendEmail方法中加入暂停3秒钟，请问先打印邮件还是短信
 * ---------senEmail
 * ---------sendSMS
 *
 * 3.添加一个普通的hello方法，请问先打印邮件还是hello
 * -----------hello
 * ---------senEmail
 *
 * 4.有两部手机，请问先打印邮件还是短信？
 * ---------sendSMS
 * ---------senEmail
 *
 * 5.有两个静态同步方法，有1部手机，请问先打印邮件还是短信？
 * ---------senEmail
 * ---------sendSMS
 *
 * 6.有两个静态同步方法，有2部手机，请问先打印邮件还是短信？
 * ---------senEmail
 * ---------sendSMS
 *
 * 7.有1个静态同步方法，有1个普通同步方法，有1部手机，请问先打印邮件还是短信？
 * ---------sendSMS
 * ---------senEmail
 *
 * 8.有1个静态同步方法，有1个普通同步方法，有2部手机，请问先打印邮件还是短信？
 * ---------sendSMS
 * ---------senEmail
 *
 *
 * 作用于实例方法，当前实例加锁，进入同步代码前要获取当前实例的锁；
 * 作用于代码块，对括号里配置的对象加锁；
 * 对应静态代码块，当前类加锁，进去同步代码块前要获得当前类对象的锁；
 */
public class lock8Demo {
    @SneakyThrows
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(()->{
            phone.sendEmail();
        },"a").start();

        TimeUnit.MILLISECONDS.sleep(200);

        new Thread(()->{
//            phone.sendSMS();
//            phone.hello();
            phone2.sendSMS();
        },"b").start();
    }
}

class Phone{
    @SneakyThrows
    public static synchronized void sendEmail() {
        TimeUnit.SECONDS.sleep(3);
        System.out.println("---------senEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("---------sendSMS");
    }

    public void hello() {
        System.out.println("-----------hello");
    }
}