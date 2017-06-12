package com.zrb;

import org.junit.Test;

/**
 * Created by Zhangrb on 2017/6/12.
 */
public class RepeatQuickSort {
    @Test
    public void test1(){
        int a[]={49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34,15,35,25,53,51};
        if(a.length > 0){
            _quickSort(a,0,a.length-1);
        }
        for (int i = 0 ; i < a.length ; i++){
            System.out.print(a[i] + " ");
        }

    }
    public int getMiddle(int[] a , int low, int high){

        int temp = a[low];

        while (low < high){
            while (low < high && a[high] >= temp){
                high--;
            }
            a[low] = a[high];
            while (low < high && a[low] <= temp){
                low++;
            }
            a[high] = a[low];
        }

        a[low] = temp;
        return low;
    }

    public void _quickSort(int[] a, int low, int high){
        if(low < high){
            int middle = getMiddle(a, low, high);
            _quickSort(a,low,middle - 1);
            _quickSort(a,middle + 1, high);
        }

    }
}
