package com.example.nuvi_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NuviDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NuviDemoApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){ //PasswordEncoder란  패스워드를 암호화 하는 방식이다. 평문 저장을 막기 위함
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
