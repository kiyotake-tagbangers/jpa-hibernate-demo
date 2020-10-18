package com.kiyotagbangers.jpahibernatedemo;

import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import com.kiyotagbangers.jpahibernatedemo.entity.FullTimeEmployee;
import com.kiyotagbangers.jpahibernatedemo.entity.PartTimeEmployee;
import com.kiyotagbangers.jpahibernatedemo.entity.Student;
import com.kiyotagbangers.jpahibernatedemo.repository.CourseRepository;
import com.kiyotagbangers.jpahibernatedemo.repository.EmployeeRepository;
import com.kiyotagbangers.jpahibernatedemo.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class JpaHibernateDemoApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

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

        // studentRepository.saveStudentWithPassport();

        // courseRepository.addHardcodedReviewsForCourse();
        // List<Review> reviews = new ArrayList<>();
        // reviews.add(new Review("5", "Great Hands-on"));
        // reviews.add(new Review("5", "Hotsoff"));

        // courseRepository.addReviewsForCourse(10003L, reviews);

        // studentRepository.insertHardcodedStudentAndCourse();
        // studentRepository.insertStudentAndCourse(new Student("Tanakayama"), new Course("Microservices"));

        employeeRepository.insert(new FullTimeEmployee("Yoshikawa", new BigDecimal("10000")));
        employeeRepository.insert(new PartTimeEmployee("Yamakawa", new BigDecimal("50")));

        // comment-out when @MappedSuperclass uses
        // logger.info("All Employees -> {}", employeeRepository.retrieveAllEmployees());
        logger.info("All Employees -> {}", employeeRepository.retrieveAllPartTimeEmployees());
        logger.info("All Employees -> {}", employeeRepository.retrieveFullPartTimeEmployees());
    }
}
