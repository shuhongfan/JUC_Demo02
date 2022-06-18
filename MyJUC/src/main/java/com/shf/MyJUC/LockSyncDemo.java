package com.shf.MyJUC;

public class LockSyncDemo {
    public static void main(String[] args) {

    }

    Object object = new Object();
    Book book = new Book();
    public void m1(){

        synchronized (book){
            System.out.println("-----hello synchronized code block");
            throw new RuntimeException("-------exp");
        }
    }

    public class Book{}
}
