package org.example.api_gateway;

import org.example.api_gateway.models.User;
import org.example.api_gateway.models.enums.Gender;
import org.example.api_gateway.models.enums.Role;
import org.example.api_gateway.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ApiGatewayApplication {


	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public CommandLineRunner createAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			String adminLogin = "admin";
			if (userRepository.findByLogin(adminLogin).isEmpty()) {
				User admin = new User();
				admin.setLogin(adminLogin);
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRole(Role.ADMIN);
				admin.setGender(Gender.MALE);
				userRepository.save(admin);
				System.out.println("Admin user created");
			} else {
				System.out.println("Admin user already exists");
			}
		};
	}

}
