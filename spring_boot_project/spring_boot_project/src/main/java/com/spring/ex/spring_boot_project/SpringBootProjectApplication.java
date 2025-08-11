package com.spring.ex.spring_boot_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.ex.sms", "com.ex.spring.spring_boot_project"})
@EnableJpaRepositories(basePackages = "com.ex.sms.repository")
@EntityScan(basePackages = "com.ex.sms.entity")
public class SpringBootProjectApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}
}
