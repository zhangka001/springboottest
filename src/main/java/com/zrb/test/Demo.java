package com.zrb.test;

/**
 * 匿名内部类的使用方式,不需要实现类
 * Created by zrb on 2017/5/26.
 */
abstract interface Person{

    void eat();

        }
public class Demo {
    public static void main(String[] args){
        Person p = new Person() {
            @Override
            public void eat() {
                System.out.println("I eat fish !");
            }
        };
        p.eat();
    }
}
