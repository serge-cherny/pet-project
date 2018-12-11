package com.videobet.sandbox;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FizzBuzz {
    private int i;
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();
    private Lock lock3 = new ReentrantLock();
    private Lock lock4 = new ReentrantLock();
    private Lock lock5 = new ReentrantLock();
    private Thread thread1;
    private Thread thread2;
    private Thread thread3;
    private Thread thread4;

    public void print(int n) {
        lock1.lock();
        lock3.lock();
        lock4.lock();
        lock5.lock();
        thread1 = new Thread(() -> {
            while (true) {
                lock1.lock();
                lock2.lock();
                if (i % 3 == 0 && i % 5 != 0) System.out.println("Fizz");
                lock2.unlock();
            }
        }
        );
        thread2 = new Thread(() -> {
            while (true) {
                lock2.lock();
                lock3.lock();
                if (i % 3 != 0 && i % 5 == 0) System.out.println("Buzz");
                lock3.unlock();
            }
        }
        );
        thread3 = new Thread(() -> {
            while (true) {
                lock3.lock();
                lock4.unlock();
                if (i % 3 == 0 && i % 5 == 0) System.out.println("FizzBuzz");
                lock4.unlock();
            }
        }
        );
        thread4 = new Thread(() -> {
            while (true) {
                lock4.lock();
                lock5.lock();
                if (i % 3 != 0 && i % 5 != 0) System.out.println(i);
                lock5.unlock();
            }
        }
        );
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        for (i = 1; i <= n; i++) {
            lock1.unlock();
            lock5.lock();
        }

        thread1.interrupt();
        thread2.interrupt();
        thread3.interrupt();
        thread4.interrupt();

    }
}
