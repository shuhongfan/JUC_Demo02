package com.shf.myjuc2.cas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@AllArgsConstructor
@ToString
class User {
    String userName;
    int age;
}

/**
 * CAS是实现自旋锁的基础，自旋翻译成人话就是循环，一般是用一个无限循环实现。这样一来，一个无限循环中，执行一个
 * CAS操作，
 * 当操作成功返回true时，循环结束;
 * 当返回false 时，接着执行循环，继续尝试CAS操作，直到返回true。
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();

        User z3 = new User("z3", 22);
        User li4 = new User("li4", 28);

        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());
    }
}
