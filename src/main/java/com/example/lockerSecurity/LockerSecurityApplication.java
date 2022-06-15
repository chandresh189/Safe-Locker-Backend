package com.example.lockerSecurity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.lockerSecurity.Repository.UserRepository;
import com.example.lockerSecurity.entity.User;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class LockerSecurityApplication {
	
	@Autowired
	private UserRepository userRepository;
	
//	@PostConstruct
//	public void initUsers() {
//		List<User> users = Stream.of(
//				new User(101,"bharat","123","bharat@gmail.com","LRLL",1234),
//				new User(102,"chandresh","123","chandresh@gmail.com","RRLL",4321)
//				).collect(Collectors.toList());
//		userRepository.saveAll(users);
//	}
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/*").allowedHeaders("*").allowedOrigins("http://localhost:4200").allowCredentials(true);
//			}
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(LockerSecurityApplication.class, args);
	}

}
