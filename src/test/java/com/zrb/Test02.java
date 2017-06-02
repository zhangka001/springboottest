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

    List<Employee> employees = Arrays.asList(new Employee("张三",18,5555.555),
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

    @Test
    public void test01(){
        List<Employee> filter = filter(employees, new FilterByAge());
        System.out.println(filter);
        System.out.println("=======================");
        List<Employee> filter1 = filter(employees, new FilterBySalary());
        System.out.println(filter1);
    }
}
