package com.eucl.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import com.eucl.example.role.Role;
import com.eucl.example.role.RoleRepository;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class euclApplication {

	public static void main(String[] args) {
		SpringApplication.run(euclApplication.class, args);
	}



	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("ROLE_CUSTOMER").isEmpty()) {
				roleRepository.save(
						Role.builder().name("ROLE_CUSTOMER").build()
				);
			}
		};
	}
}
