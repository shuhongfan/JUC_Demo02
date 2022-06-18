package com.shf.MyJUC;

import lombok.*;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        /**
         * 原子引用
         */
        AtomicReference<User> userAtomicReference = new AtomicReference<>();
        User z3 = new User("z3", 22);
        User lisi = new User("lisi", 28);

        userAtomicReference.set(z3);

        System.out.println(userAtomicReference.compareAndSet(z3, lisi)+"\t"+userAtomicReference.get());
        System.out.println(userAtomicReference.compareAndSet(z3, lisi)+"\t"+userAtomicReference.get());
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User{
    String userName;
    int age;
}
