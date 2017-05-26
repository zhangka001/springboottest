package com.zrb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.zrb.Mapper")
public class SpringboottestApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringboottestApplication.class, args);
		System.out.println("hello!!!");

	}
}
