package com.zrb;

import com.zrb.domain.Employee;
import com.zrb.service.FilterByAge;
import com.zrb.service.FilterBySalary;
import com.zrb.service.Mypredicates;
import org.junit.Test;
import org.springframework.test.context.TestExecutionListeners;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zrb on 2017/5/29.
 */
public class Test02 {

    List<Employee> employees = Arrays.asList(
            new Employee("张三",18,5555.555),
            new Employee("李四",28,6666.555),
            new Employee("王五",38,7777.555),
            new Employee("赵六",48,8888.555));


    public List<Employee> filter(List<Employee> list, Mypredicates<Employee> mp){
        List<Employee> emps = new ArrayList<>();
       for(Employee emp : list){
           if(mp.test(emp)){
               emps.add(emp);
           }
       }
       
       return emps;
    }
    @Test
    public void test32(){

        employees.stream().filter(e->e.getAge()>20).forEach(e->System.out.println(e));


    }

    /**
     * 优化模式一：策略设计模式，创建新的类和方法
     */
    @Test
    public void test01(){
        List<Employee> filter = filter(employees, new FilterByAge());
        System.out.println(filter);
        System.out.println("=======================");
        List<Employee> filter1 = filter(employees, new FilterBySalary());
        System.out.println(filter1);
    }

    /**
     * 优化模式二:匿名内部类,可以少创建一个实现类，但是接口中只能有一个方法
     */

    @Test
    public void test04(){
        List<Employee> list = filter(employees, new Mypredicates<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getAge()> 30;
            }
        });

        System.out.println(list.toString());
    }

    /**
     * 优化方式三：简单的lambda表达式
     */

    @Test
    public void test05(){
        List<Employee> list = filter(employees,e->e.getAge() > 30);
        System.out.println(list.toString());
        list.forEach(System.out::println);
    }

    /**
     * lambda表达式
     */

    @Test
    public void test06(){
        employees.stream().filter(e->e.getSalary() > 6000).limit(1).forEach(System.out::println);
        System.out.println("=====================");

        employees.stream().map(Employee::getAge).forEach(System.out::println);
    }









}
