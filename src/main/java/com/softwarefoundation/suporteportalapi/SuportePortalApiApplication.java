package com.softwarefoundation.suporteportalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SuportePortalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuportePortalApiApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
