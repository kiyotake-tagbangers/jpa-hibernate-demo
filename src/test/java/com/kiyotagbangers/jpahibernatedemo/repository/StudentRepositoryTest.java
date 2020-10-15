package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.JpaHibernateDemoApplication;
import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import com.kiyotagbangers.jpahibernatedemo.entity.Passport;
import com.kiyotagbangers.jpahibernatedemo.entity.Student;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class StudentRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentRepository repository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional // entire test is within a transaction
    void retrieveStudentAndPassportDetails() {
        Student student = em.find(Student.class, 20001L);

        // OneToOne relationship is always eager fetch, student details and passport details are also retrieved
        logger.info("student -> {}", student);

        // If Student passport field fetch strategy is LAZY and @Transactional is comment-out, LazyInitializationException happened. Because we are not having any transaction
        logger.info("passport -> {}", student.getPassport());
    }

    @Test
    @Transactional // entire test is within a transaction
    void retrievePassportAndAssociatedStudent() {
        Passport passport = em.find(Passport.class, 30001L);

        logger.info("passport -> {}", passport);
        logger.info("student -> {}", passport.getStudent() );
    }

    @Test
    void someTest() {
        repository.someOperationToUnderstandPersistenceContext();
    }
}