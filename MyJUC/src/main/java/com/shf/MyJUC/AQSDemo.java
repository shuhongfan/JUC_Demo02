package com.shf.MyJUC;

import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }
}
