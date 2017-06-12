package com.zrb;

/**
 * 模拟 CAS 算法
 */
public class TestCompareAndSwap {
    public static void main(String[] args){
        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0 ; i < 10 ; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 101));
                    System.out.println(b);
                }
            }).start();
        }
    }
}

class CompareAndSwap{
    //多个线程来都会得到的值
    private int value;
    //但是只有一个线程才会调用compareAndSet(),

    //获取内存值
    public synchronized int get(){
        return value;
    }
    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value;

        if(oldValue == expectedValue){
            //第一个线程已经改变了value，所有当第二个线程获取到的value，已经不是在线程开始的value了
            this.value = newValue;
        }
        System.out.println("从內存拿到的值" + oldValue);
        return oldValue;
    }

    //判断是否设置成功
    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        System.out.println("expectedValue:" + expectedValue + "  " + "newValue" + newValue);
        return expectedValue == compareAndSwap(expectedValue,newValue);
    }

}