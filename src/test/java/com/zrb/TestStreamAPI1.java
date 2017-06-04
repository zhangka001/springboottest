package com.zrb;

import com.zrb.domain.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by zrb on 2017/6/2.
 */
public class TestStreamAPI1 {

    //创建流
    @Test
    public void test1(){
        //1. 可以通过Collection 系列集合提供的stream() 或 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2. 通过Arrays 中的静态方法stream() 获取数组流
        Employee[] emps = new Employee[10];
        Stream<Employee> steam2 = Arrays.stream(emps);

        //3. 通过Stream类中静态方法 of()
        Stream<String> stream3 = Stream.of("aa","b");

        //4. 创建无限流
        //迭代
        Stream<Integer> stream4 = Stream.iterate(0,(x) -> x + 2);
        stream4.limit(5).forEach(System.out::println);

        //生成
        Stream.generate(() -> Math.random()).limit(18).forEach(System.out::println);
    }
}
