package com.shf.myjuc2.jvm;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class JOLDemo {
    public static void main(String[] args) {
        System.out.println(VM.current().details());

        Object o = new Object();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());


        Customer c1 = new Customer();
        System.out.println(ClassLayout.parseInstance(c1).toPrintable());
    }
}

//只有一个对象头的实例对象,只有一个对象头的实例对象，16字节（忽略压缩指针的影响）+4字节+1字节=21字节--------》对齐填充，24字节
class Customer {
//    1. 第一种情况，只有对象头，没有其他任何实例数据

//    2. 第二种情况，int+boolean，默认满足对其填充，24 bytes
    int id;
    boolean flag = false;
}