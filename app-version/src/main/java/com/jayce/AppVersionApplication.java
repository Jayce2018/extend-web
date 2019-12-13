package com.jayce;

import com.jayce.common.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@ComponentScan(basePackages = {"com.jayce.*"})
@MapperScan("com.jayce.common.*")
@SpringBootApplication
public class AppVersionApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(AppVersionApplication.class, args);
		SpringContextUtil.setApplicationContext(applicationContext);
	}

}
