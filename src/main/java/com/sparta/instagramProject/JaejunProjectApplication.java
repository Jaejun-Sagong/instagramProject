package com.sparta.instagramProject;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class JaejunProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JaejunProjectApplication.class, args);
	}

}
