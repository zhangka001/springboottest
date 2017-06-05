package com.zrb;

import com.zrb.domain.Employee;

import com.zrb.service.Trader;
import com.zrb.service.Transaction;
import org.junit.Test;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by zrb on 2017/6/4.
 */
public class TestStreamAPI3 {
    List<Employee> emps = Arrays.asList(
            new Employee("李四",28,6666.555),
            new Employee("张三",18,5555.555),

            new Employee("王五",38,7777.555),
            new Employee("赵六",48,8888.555),
            new Employee("赵六",48,8888.555));


    //需求：搜索名字中 “六” 出现的次数
    @Test
    public void test1(){
        Optional<Integer> sum = emps.stream().map(Employee::getName).map((str) -> {
            if (str.contains("六")) {
                return 1;
            }else {
                return 0;
            }
        }).reduce(Integer::sum);
        System.out.println(sum.get());
    }

    //collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法

    @Test
    public void test2(){
        //将员工的名字放大list集合中
        List<String> collect = emps.stream().map(Employee::getName).collect(Collectors.toList());
        collect.stream().forEach(System.out::println);
        int size = emps.stream().map(Employee::getName).collect(Collectors.toSet()).size();
        System.out.println(size);
        System.out.println("----------------------------");

        HashSet<String> collect1 = emps.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        collect1.forEach(System.out::println);


    }

    @Test
    public void test3(){
        Optional<Double> collect = emps.stream().map(Employee::getSalary).collect(Collectors.maxBy(Double::compare));
        System.out.println(collect.get());

        System.out.println("---------------------");

        Optional<Employee> collect1 = emps.stream().collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));

        System.out.println(collect1.get());

        Double collect2 = emps.stream().collect(Collectors.summingDouble(Employee::getSalary));

        System.out.println(collect2);

        Double collect3 = emps.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(collect3);

        Long collect4 = emps.stream().collect(Collectors.counting());
        System.out.println(collect4);

        List<Map<String, Object>> collect5 = emps.stream().map((e) -> {

            BeanMap beanMap = BeanMap.create(e);
            Set<Map.Entry> set1 = beanMap.entrySet();
            Map<String, Object> collect6 = set1.stream().collect(Collectors.toMap(s -> (String)s.getKey(), s -> s.getValue()));
            return collect6;

        }).collect(Collectors.toList());
        System.out.println(collect5);



    }

    @Test
    public void test4(){
        Integer[] num = new Integer[]{1,2,3,4};
        Arrays.stream(num).map(x -> x * x).forEach(System.out::println);

        Optional<Integer> reduce = emps.stream().map(e -> 1).reduce(Integer::sum);
        System.out.println(reduce.get());
    }


    @Test
    public void before() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> list = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );


    //1. 找出2011年发生的所有交易， 并按交易额排序（从低到高）

        list.stream().filter(e -> e.getYear() == 2011).sorted((e1, e2) -> Integer.compare(e1.getValue(), e2.getValue())).forEach(System.out::println);

    //2. 交易员都在哪些不同的城市工作过？

        list.stream().map(e -> e.getTrader()).map(s -> s.getCity()).collect(Collectors.toCollection(HashSet::new)).forEach(System.out::println);
        list.stream().map(e -> e.getTrader().getCity()).distinct().forEach(System.out::println);
    //3. 查找所有来自剑桥的交易员，并按姓名排序
        System.out.println("-----------------------");
        list.stream().filter(e -> e.getTrader().getCity().equals("Cambridge")).map(e -> e.getTrader().getName()).distinct()
                .sorted((s1,s2) -> s1.compareTo(s2)).forEach(System.out::println);


    //4. 返回所有交易员的姓名字符串，按字母顺序排序
        String reduce = list.stream().map(e -> e.getTrader().getName()).distinct().sorted((e1, e2) -> e1.compareTo(e2)).reduce("", String::concat);
        System.out.println(reduce);
    System.out.println("---------------------------------");
        list.stream().map(e -> e.getTrader().getName()).flatMap(TestStreamAPI3::filterCharacter).distinct().sorted((e1, e2) -> e1.compareTo(e2)).forEach(System.out::println);



        //5. 有没有交易员是在米兰工作的？

        boolean mario1 = list.stream().anyMatch(e -> e.getTrader().getCity().equals("Mario"));
        System.out.println(mario1);


        //6. 打印生活在剑桥的交易员的所有交易额

        Long cambridge = list.stream().filter(e -> e.getTrader().getCity().equals("Cambridge")).collect(Collectors.summingLong(Transaction::getValue));
        System.out.println(cambridge);
        Optional<Integer> cambridge1 = list.stream().filter(e -> e.getTrader().getCity().equals("Cambridge")).map(e -> e.getValue()).reduce(Integer::sum);

        System.out.println(cambridge1);
        System.out.println("---------------------------------");
        //7. 所有交易中，最高的交易额是多少

        Optional<Integer> collect = list.stream().map(e -> e.getValue()).collect(Collectors.maxBy((e1, e2) -> e1.compareTo(e2)));
        System.out.println(collect.get());
        //8. 找到交易额最小的交易

        Optional<Integer> min = list.stream().map(e -> e.getValue()).min(Integer::compareTo);
        System.out.println(min.get());

    }

    public static Stream<String> filterCharacter(String str){
        List<String> list = new ArrayList<>();

        for (Character ch : str.toCharArray()) {
            list.add(ch.toString());
        }

        return list.stream();
    }




}
