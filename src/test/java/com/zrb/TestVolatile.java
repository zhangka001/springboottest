package com.zrb;

/**
 * volatile : 当多个线程操作共享数据 ，可以保证内存中的数据是可见的
 * 注意：
 *      volatile：不具有互斥性，是轻量级的同步策略
 *                不能保证原子性
 */

public class TestVolatile {

    public static void main(String[] args){
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true){
            if(td.isFlag()){
                System.out.println("--------------");
                break;
            }
        }
    }

}

class ThreadDemo implements Runnable{
    private volatile boolean flag = false;

    @Override
    public void run(){
        flag = true;
        System.out.println("flag = " + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}