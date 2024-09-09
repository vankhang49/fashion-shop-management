package com.codegym.fashionshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.codegym.fashionshop.repository")
public class FashionShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionShopApplication.class, args);
	}

}
