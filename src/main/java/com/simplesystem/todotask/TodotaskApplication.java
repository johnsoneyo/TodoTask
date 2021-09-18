package com.simplesystem.todotask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TodotaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodotaskApplication.class, args);
	}

}
