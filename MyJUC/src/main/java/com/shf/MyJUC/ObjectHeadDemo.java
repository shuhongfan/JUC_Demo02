package com.shf.MyJUC;

public class ObjectHeadDemo {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(o.hashCode());

        synchronized (o){

        }
    }
}
