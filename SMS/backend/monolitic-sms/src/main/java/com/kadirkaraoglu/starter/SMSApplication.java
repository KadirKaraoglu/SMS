package com.kadirkaraoglu.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.kadirkaraoglu"})
@EnableJpaRepositories(basePackages = "com.kadirkaraoglu.repository")
@EntityScan(basePackages = {"com.kadirkaraoglu"})
@SpringBootApplication()
public class SMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(SMSApplication.class, args);
	}

}
