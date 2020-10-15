package com.kiyotagbangers.jpahibernatedemo;

import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import com.kiyotagbangers.jpahibernatedemo.repository.CourseRepository;
import com.kiyotagbangers.jpahibernatedemo.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaHibernateDemoApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaHibernateDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Course course = courseRepository.findById(10001L);
		// logger.info("Course 10001 -> {}", course);

		// courseRepository.save(new Course("Microservices practice"));
		// courseRepository.deleteById(10001L);
		// courseRepository.playWithEntityManager();
		studentRepository.saveStudentWithPassport();
	}
}
