package com.project.trashure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = DataSourceAutoConfiguration.class)
public class SpringTrashureProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTrashureProjectApplication.class, args);
	}

}
