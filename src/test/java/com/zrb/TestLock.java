package com.zrb;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 解决多线程安全的问题
 * synchronized:隐式锁
 * 1.同步代码块
 * 2.同步方法
 *
 * jdk1.5之后
 * 3.同步锁Lock
 * 注意：It is obvious lock ,so it need the method "lock()" to lock ,at the same time , must
 * use the method "unlock" to release the lock .
 */
public class TestLock {
    public static void main(String[] args){
        Ticket ticket = new Ticket();
        new Thread(ticket,"1号窗口").start();
        new Thread(ticket,"2号窗口").start();
        new Thread(ticket,"3号窗口").start();
    }
}


class Ticket implements Runnable{

    private int num = 100;
    private Lock lock = new ReentrantLock();
    @Override
    public void run() {
        while (true){
            lock.lock();
            try {
                Thread.sleep(100);
                if(num >0){
                    System.out.println(Thread.currentThread().getName() + "火车票剩余：" + --num);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }


        }

    }
}