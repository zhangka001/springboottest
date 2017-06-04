package com.zrb;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.zrb.domain.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by zrb on 2017/6/2.
 */
public class TestStreamAPI2 {


    /*
	  筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。当满足条件的数据的个数，就不会再循环下去，这叫做短路
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	 */

    //内部迭代：迭代操作 Stream API 内部完成

    //2. 中间操作

    List<Employee> emps = Arrays.asList(
            new Employee("李四",28,6666.555),
            new Employee("张三",18,5555.555),

            new Employee("王五",38,7777.555),
            new Employee("赵六",48,8888.555));

    @Test
    public void test2(){
        //所有的中间操作不会做任何的处理
        Stream<Employee> stream = emps.stream()
                .filter((e) -> {
                    System.out.println("测试中间操作");
                    return e.getAge() <= 35;
                });

        //只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        stream.forEach(System.out::println);
    }

    //外部迭代
    @Test
    public void test3(){
        Iterator<Employee> it = emps.iterator();

        while(it.hasNext()){
            System.out.println(it.next());
        }
    }

    @Test
    public void test4(){
        emps.stream()
                .filter((e) -> {
                    System.out.println("短路！"); // &&  ||
                    return e.getSalary() >= 5000;
                }).limit(3)
                .forEach(System.out::println);
    }

    @Test
    public void test5(){
        emps.parallelStream()
                .filter((e) -> e.getSalary() >= 5000)
                .skip(2)
                .forEach(System.out::println);
    }

    @Test
    public void test6(){
        emps.stream()
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 映射
     * map -- 接收 Lambda ， 将元素转换成其他形式或提取消息，接收一个函数作为参数，该函数会被应用到每一个元素上，并将其映射成一个新的元素
     * flatMap -- 接收一个函数作为参数，将流中的每一个值都换成另一个流，然后把所有流连接成一个流
     */

    @Test
    public void test7(){
        emps.stream().map((e) -> e.getName()).forEach(System.out::println);
        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        strList.stream().map(String::toUpperCase).forEach(System.out::println);

        Stream<Stream<Character>> stream1 = strList.stream().map(TestStreamAPI2::filterCharater);

        stream1.forEach((sm) -> {
            sm.forEach(System.out::println);
        });
        System.out.println("=============");
        Stream<Character> characterStream = strList.stream().flatMap(TestStreamAPI2::filterCharater);
        characterStream.forEach(System.out::println);

    }

    public static Stream<Character> filterCharater(String str){
        List<Character> list = new ArrayList<>();
        for (Character c : str.toCharArray()){
            list.add(c);
        }
        return list.stream();
    }


    /**
     * 排序
     * sorted() -- 自然排序(Comparable)
     * sorted(Comparator ) -- 定制排序(Comparator)
     */

    @Test
    public void test8(){
        List<String> strList = Arrays.asList("eee","aaa", "bbb", "ccc", "ddd");
        strList.stream().sorted().forEach(System.out::println);

        System.out.println("------------------------------");

        emps.stream().sorted((e1,e2) -> {

            if(e1.getAge().equals(e2.getAge())){
                return e1.getName().compareTo(e2.getName());
            }else {
                return e1.getAge().compareTo(e2.getAge());
            }

        }).forEach(System.out::println);
    }

}






















