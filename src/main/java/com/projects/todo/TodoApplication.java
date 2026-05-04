package com.projects.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * @SpringBootApplication is a shortcut annotation that does 3 things:
 *
 *   1. @Configuration      — This class can define Spring "beans" (objects Spring manages)
 *   2. @EnableAutoConfiguration — Automatically sets up things based on your dependencies.
 *                                 For example: sees H2 on classpath → sets up a database.
 *   3. @ComponentScan      — Scans this package for classes annotated with
 *                            @RestController, @Repository, etc. and registers them.
 *
 * In short: this one annotation boots up the whole application.
 */
@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		// This starts the embedded Tomcat server and your Spring app.
		// You don't need to deploy a WAR file anywhere — just run this!
		SpringApplication.run(TodoApplication.class, args);
	}
}