package com.springboot.postgres.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository respository) {
		return args -> {
			Student student1 = new Student("but1", 
										"bnb1@gmail.com",
										LocalDate.of(2000, Month.MARCH, 5));
			Student student2 = new Student("but2", 
					"bnb2@gmail.com",
					LocalDate.of(2001, Month.APRIL, 15));
			respository.saveAll(List.of(student1, student2));
		};
	}
}
