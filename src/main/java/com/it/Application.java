package com.it;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @ClassName: Application  
 * @Description: TODO  启动类
 * @author Administrator  
 * @date 2019年5月22日  
 *
 */
@EnableScheduling
@MapperScan("com.it.dao.*")
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
