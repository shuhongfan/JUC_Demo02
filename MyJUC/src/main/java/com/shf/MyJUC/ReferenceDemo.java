package com.shf.MyJUC;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ReferenceDemo {
    public static void main(String[] args) {
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<MyObject>(myObject, referenceQueue);

        System.out.println(phantomReference.get());

        ArrayList<byte[]> list = new ArrayList<>();

        new Thread(() -> {
            while (true) {
                list.add(new byte[1 * 1024 * 1024]);

                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(phantomReference.get()+"\t list add ok");
            }
        },"t1").start();


        new Thread(()->{
            while (true){
                Reference<? extends MyObject> reference = referenceQueue.poll();
                if (reference!=null){
                    System.out.println("----虚对象加入了对象");
                }
            }
        },"t2").start();
    }


    public static void weakReference(String[] args) {
//        软引用
//        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());

//        弱引用
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());

        System.gc();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("--------gc after内存够用" + weakReference.get());
    }

    public static void strongReference(String[] args) {
        MyObject myObject = new MyObject();
        System.out.println("gc before:" + myObject);

        myObject = null;

//        人工开启GC
        System.gc();
    }
}

class MyObject {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("--------invoke finalize method!!!");
    }
}
