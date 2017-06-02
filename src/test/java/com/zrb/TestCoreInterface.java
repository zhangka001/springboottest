package com.zrb;

import jdk.nashorn.internal.ir.FunctionCall;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by zrb on 2017/6/2.
 *
 * Java8 内置的四大核心函数式接口
 *
 * Consumer<T> : 消费型接口
 *         void accept（T t）;
 * Supplier<T> : 供给型接口
 *         T get();
 * Function<T,R> : 函数型接口
 *         R apply(T t);
 * Predicate<T> : 断言型接口
 *         boolean test(T t);
 *
 */
public class TestCoreInterface {

    //Consumer<T> 消费型接口
    public void happy(double money, Consumer<Double> mon){
        mon.accept(money);
    }

    @Test
    public void test01(){
        happy(1000,(x)->System.out.println("喜欢大宝剑,每次消费" + x));
    }

    //Supplier<T> : 供给型接口
    //产生指定个数的证书，并放入到集合中
    public List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> list = new ArrayList<>();
        for (int i = 0 ;i < num ; i++){
            list.add(sup.get());
        }
        return list;
    }

    @Test
    public void test02(){
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        numList.forEach(System.out::println);
    }

    //Function<T,R> : 函数型接口

    public String strHandler(String str, Function<String,String> fun){
        return  fun.apply(str);
    }
    @Test
    public void test03(){
        String s = strHandler("\t\t\t    天空是蓝的   ", (str) -> str.trim());
        System.out.println(s);
        String dd = strHandler("天空是蓝的", (str) -> str.substring(3));
        System.out.println(dd);
    }

    //Predicate<T> : 断言型接口
    public List<String> filerStr(List<String> listStr , Predicate<String> pre){
        List<String> list = new ArrayList<>();
        for (String s : listStr){
            if(pre.test(s)){
                list.add(s);
            }
        }
        return list;
    }

    @Test
    public void test04(){
        List<String> list = Arrays.asList("aaass", "asdfas", "a", "ss", "fffff");
        List<String> list1 = filerStr(list, (str) -> str.length() >= 2);
        list1.forEach(System.out::println);
    }
}
