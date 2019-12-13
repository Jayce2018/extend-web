package com.jayce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.jayce"})
@SpringBootApplication
public class Stable1Application {

	public static void main(String[] args) {
		SpringApplication.run(Stable1Application.class, args);
	}

}
