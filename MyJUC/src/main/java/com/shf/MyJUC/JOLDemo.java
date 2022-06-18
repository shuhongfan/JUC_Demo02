package com.shf.MyJUC;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.concurrent.TimeUnit;

public class JOLDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(VM.current().details());

        System.out.println(VM.current().objectAlignment());

        TimeUnit.SECONDS.sleep(5);
        Object o = new Object();
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}

class Customer{
    int id;
    boolean flag=  false;
}
