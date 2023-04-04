package com.ruso.zapicito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ZapicitoApplication {

	public static void main(String[] args) {
		System.out.println("dsdjfsdlkfjdskfjdslkjdslkjdslkj");
		SpringApplication.run(ZapicitoApplication.class, args);
	}

}
