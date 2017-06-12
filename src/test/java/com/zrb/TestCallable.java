package com.zrb;

import java.util.InputMismatchException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 一、创建执行的线程方式三：实现Callable 借口。相较于实现Runnable接口的方式，方法可以有返回值，并且可以抛出异常
 * 二、执行Callable方式，需要FutureTask 实现类的支持，用于接收运算结果，FutrueTask是Futrue 接口的实现类
 *
 */
public class TestCallable {

    public static void main(String[] args){
        ThreadDemo1 td1 = new ThreadDemo1();
        //执行Callable方式，需要FutureTask 实现类的支持，用于接收运算结果
        FutureTask<Integer> ft = new FutureTask<>(td1); //也可以用于闭锁
        new Thread(ft).start();
        try {
            Integer result = ft.get();
            System.out.println(result); //只有在线程执行完之后，才会获取结果，也可以用于闭锁。
            System.out.println("-------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

class ThreadDemo1 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i =0; i <= 1000000; i++){
            sum += i;
        }
        return sum;
    }
}

//class ThreadDemo1 implements Runnable{
//
//    @Override
//    public void run() {
//
//    }
//}