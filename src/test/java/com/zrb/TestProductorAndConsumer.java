package com.zrb;

import java.util.Locale;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者和消费者
 */
public class TestProductorAndConsumer {
    public static void main(String[] args){
        Clerk clerk = new Clerk();

        Productor pro = new Productor(clerk);
        Consumer con = new Consumer(clerk);

        new Thread(pro,"生产者A").start();
        new Thread(con,"消费者A").start();

        new Thread(pro,"生产者B").start();
        new Thread(con,"消费者B").start();

    }
}

class Clerk{
    private int product = 0;
    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    //进货
    public  void get(){
        lock.lock();
        try {
            while (product >= 1){
                System.out.println("产品已满！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
           condition.signalAll();
        }finally {
            lock.unlock();
        }


    }

    //卖货
    public  void sale(){
        lock.lock();
        try{
            while (product <= 0){
                System.out.println("缺货");
                try {
                    condition.await();
                } catch (InterruptedException e) {

                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            condition.signalAll();//当消费了之后，在clerk中就会有空位，所有唤醒了clerk的get方法，so the Thread of productor will
            //continue to productor the produce;

        }finally {
            lock.unlock();
        }

    }
}

class Productor implements Runnable{
    private Clerk clerk;


    public Productor(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i = 0; i < 20; i++){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
            clerk.get();
        }
    }
}

class Consumer implements Runnable{
    private Clerk clerk;
    public Consumer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run(){

        for (int i = 0; i < 20; i++){
            clerk.sale();
        }
    }
}

/*
class Clerk{
    private int product = 0;

    //进货
    public synchronized void get(){
        while (product >= 1){
            System.out.println("产品已满！");
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println(Thread.currentThread().getName() + " : " + ++product);
        this.notifyAll();

    }

    //卖货
    public synchronized void sale(){
        while (product <= 0){
            System.out.println("缺货");
            try {
                this.wait();
            } catch (InterruptedException e) {

            }
        }
        System.out.println(Thread.currentThread().getName() + " : " + --product);
        this.notifyAll();//当消费了之后，在clerk中就会有空位，所有唤醒了clerk的get方法，so the Thread of productor will
        //continue to productor the produce;

    }
}

class Productor implements Runnable{
    private Clerk clerk;
    public Productor(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i = 0; i < 20; i++){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
            clerk.get();
        }
    }
}

class Consumer implements Runnable{
    private Clerk clerk;
    public Consumer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run(){

        for (int i = 0; i < 20; i++){
            clerk.sale();
        }
    }
}*/
