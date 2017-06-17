package com.zrb;

import org.junit.Test;

/**
 * Created by Zhangrb on 2017/6/17.
 */
public class TestInt {
    int elements;
    int[] arr = new int[50];
    public void insert(int value){
        int i;

        for(i = 0; i< elements ; i++){
            if(arr[i] > value){
                break;
            }
        }

        for (int j = elements ; j > i ; j--){
            arr[j] = arr[j-1];
        }
        arr[i] = value;
        elements ++;

    }

    public void display(){
        for (int i = 0 ; i < elements;i++){
            System.out.println(arr[i]);
        }
    }

    @Test
    public void test(){
        TestInt t = new TestInt();
        t.insert(90);
        t.insert(60);
        t.insert(20);
        t.insert(70);
        t.insert(30);
        t.display();

    }

}
