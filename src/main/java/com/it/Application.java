package com.it;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;


@MapperScan("com.it.dao")
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,DruidDataSourceAutoConfigure.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
