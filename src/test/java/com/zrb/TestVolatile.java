package com.zrb;

/**
 * Created by zrb on 2017/6/5.
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
    private valation boolean flag = false;

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