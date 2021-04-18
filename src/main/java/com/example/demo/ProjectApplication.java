package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @description	project
 * @author 		MinSeongHyeon
 * @since		2021/04/15
 */
@ComponentScan({
	"com.example.config", 
	"com.example.security", 
	"com.example.controller", 
	"com.example.service",
	"com.example.aop",
	"com.example.util"})
@EnableAspectJAutoProxy
@EntityScan(basePackages = "com.example.entity")
@EnableJpaRepositories(basePackages = {"com.example.repository"})
@SpringBootApplication
public class ProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
		System.out.println("================================================Project================================================");
	}
}
