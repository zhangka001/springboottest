package com.zrb.service;

import com.zrb.domain.Employee;

/**
 * Created by zrb on 2017/5/29.
 */
public class FilterBySalary implements Mypredicates<Employee>{

    @Override
    public boolean test(Employee employee) {
        return employee.getSalary()>8000;
    }
}
