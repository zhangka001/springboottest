package com.zrb;

import com.zrb.domain.Employee;
import com.zrb.service.MyFunction;
import com.zrb.service.MyFunction2;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by zrb on 2017/6/2.
 */
public class TestLambda {
    @Test
    public void test01(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!");
            }
        };

        r.run();

        System.out.println("------------------------");

        Runnable r1 = () -> System.out.println("Hello Lambda!");
        r1.run();
    }

    /**
     * 有一个参数，没有返回值
     */
    @Test
    public void test02(){
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("我是大人了！");
    }

    /**
     * 有两个以上的参数，有一个返回值,并且 Lambda 体中有多条语句
     */
    @Test
    public void test03(){
        Comparator<Integer> com = (x,y) -> {
          System.out.println("函数式接口");
          return Integer.compare(x,y);
        };
        int compare = com.compare(9, 7);
        System.out.println(compare);
    }

    /**
     * 语法格式五： 若 Lambda 体中只有一条语句，return 和 大括号都可以省略
     * 在java8中有参数列表推断，在泛型中推断出参数列表上的参数类型
     */
    @Test
    public void test04(){
        Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
        int compare = com.compare(4, 8);
        System.out.println(compare);
    }


    List<Employee> employees = Arrays.asList(
            new Employee("李四",28,6666.555),
            new Employee("张三",18,5555.555),

            new Employee("王五",38,7777.555),
            new Employee("赵六",48,8888.555));


    @Test
    public void test1(){
        Collections.sort(employees,(e1,e2) -> {
            if(e1.getAge() == e2.getAge()){
                return e1.getName().compareTo(e2.getName());
            }else {
                return Integer.compare(e1.getAge(),e2.getAge());
            }

        });

        employees.forEach(System.out::println);
    }


    public String strHandler(String str, MyFunction mt){
        return mt.getValue(str);
    }
    @Test
    public void test2(){
        String s = strHandler("\t\t\t   天空是懒得   ", (str) -> str.trim());
        System.out.println(s);
        Long l = 55l;
    }
    public String op(Long l1, Long l2, MyFunction2<Long,String> mf){
        return mf.getValue(l1,l2);
    }

    @Test
    public void test3(){
        String op = op(100L, 200L, (x, y) -> {
            Long m = x + y;
            return m.toString();
        });

        System.out.println(op);
    }

}
