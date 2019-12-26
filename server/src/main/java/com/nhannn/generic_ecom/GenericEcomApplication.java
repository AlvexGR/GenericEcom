package com.nhannn.generic_ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Author: nhannn
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GenericEcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenericEcomApplication.class, args);
	}

}
