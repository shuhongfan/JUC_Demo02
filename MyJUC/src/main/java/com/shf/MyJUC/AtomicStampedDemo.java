package com.shf.MyJUC;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicStampedReference;


@Data
@NoArgsConstructor
@AllArgsConstructor
class Book{
    int id;
    String bookName;
}


public class AtomicStampedDemo {
    public static void main(String[] args) {
        Book javaBook = new Book(1, "javaBook");

        AtomicStampedReference<Book> atomicStampedReference =
                new AtomicStampedReference<Book>(javaBook,1);


        System.out.println(atomicStampedReference.getReference()+"\t"+atomicStampedReference.getStamp());


        Book mysqlbook = new Book(2, "mysqlbook");
        boolean b = atomicStampedReference.compareAndSet(
                javaBook,
                mysqlbook,
                atomicStampedReference.getStamp(),
                atomicStampedReference.getStamp() + 1);

        System.out.println(b+"\t"+atomicStampedReference.getReference()+"\t"+atomicStampedReference.getStamp());

        b = atomicStampedReference.compareAndSet(
                mysqlbook,
                javaBook,
                atomicStampedReference.getStamp(),
                atomicStampedReference.getStamp() + 1);
        System.out.println(b+"\t"+atomicStampedReference.getReference()+"\t"+atomicStampedReference.getStamp());


    }
}
