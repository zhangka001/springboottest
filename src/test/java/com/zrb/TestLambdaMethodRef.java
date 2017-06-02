package com.zrb;


import com.zrb.domain.Employee;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * Created by zrb on 2017/6/2.
 * 一 、 方法的引用
 * //类::实例方法名
 * //类::静态方法名
 * //对象::实例方法
 *
 * 二 、 构造器的引用
 * 格式 ：
 *  ClassName::new
 *  注意：需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致！
 *
 *  三、 数组引用
 *      Type[] ::new;
 *
 */
public class TestLambdaMethodRef {


    //数组引用
    @Test
    public void test7(){
        Function<Integer,String[]> fun = (x) -> new String[x];
        String[] apply = fun.apply(10);
        System.out.println(apply.length);

        Function<Integer,String[]> fun1 = String[]::new;
        String[] apply1 = fun1.apply(34);
        System.out.println(apply1.length);


    }


    //构造器的引用
    @Test
    public void test5(){
        Supplier<Employee> sup = () -> new Employee();
        //无参
        Supplier<Employee> sup1 = Employee::new;
        //一个参数
        Function<Integer,Employee> fu = (x) -> new Employee(x);
        Employee apply = fu.apply(123);
        System.out.println(apply);

        BiFunction<String,Integer,Employee> bf = (x,y) -> new Employee(x,y);
        Employee zhangsan = bf.apply("zhangsan", 1111);

        System.out.println(zhangsan);

    }

    //类::实例方法名
    @Test
    public void test4(){
        BiPredicate<String,String> bp = (x,y) -> x.equals(y);
        //如果 Lambda 参数列表中的第一个参数是实例方法的调用者，而第二个参数是实例方法的参数时，可以使用 ClassName :: Method
        BiPredicate<String,String> bp1 = String::equals;
    }

    //类::静态方法名
    //Lambda 体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致！
    @Test
    public void test3(){
        Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
        Comparator<Integer> com1 = Integer::compare;
    }



    //对象::实例方法
    //Consumer 参数列表和返回值与printStream中println方法中的参数列表和返回值是一样的才行
    @Test
    public void test1(){
        PrintStream ps = System.out;
        Consumer<String> con = (x) -> ps.println(x);

        Consumer<String> con1 = System.out::println;
        con1.accept("Lambda表达式");
    }

    @Test
    public void test2(){
        Employee e = new Employee("zhangsan",14,1144.44);
        Supplier<String> su = () -> e.getName();
        Supplier<String> su2 = e::getName;

        System.out.println(su2.get());

        Supplier<Integer> su1 = () -> e.getAge();
        Supplier<Integer> su3 = e::getAge;
        System.out.println(su3.get());
    }
}
