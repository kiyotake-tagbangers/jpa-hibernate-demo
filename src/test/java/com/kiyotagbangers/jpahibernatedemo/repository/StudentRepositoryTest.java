package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.JpaHibernateDemoApplication;
import com.kiyotagbangers.jpahibernatedemo.entity.Passport;
import com.kiyotagbangers.jpahibernatedemo.entity.Student;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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

    @Test
    @Transactional // entire test is within a transaction
    void retrieveStudentAndCourses() {
        Student student = em.find(Student.class, 20001L);
        logger.info("student -> {}", student);
        logger.info("courses -> {}", student.getCourses());
        // LAZY fetch(ManyToMany default fetch strategy)
        //     select
        //        courses0_.student_id as student_1_5_0_,
        //        courses0_.course_id as course_i2_5_0_,
        //        course1_.id as id1_0_1_,
        //        course1_.created_date as created_2_0_1_,
        //        course1_.last_updated_date as last_upd3_0_1_,
        //        course1_.name as name4_0_1_
        //    from
        //        student_course courses0_
        //    inner join
        //        course course1_
        //            on courses0_.course_id=course1_.id
        //    where
        //        courses0_.student_id=?
        //2020-10-18 11:20:39.853  INFO 2953 --- [           main] c.k.j.repository.StudentRepositoryTest   : courses -> [Course{name='JPA practice'}, Course{name='Spring Boot practice'}]
    }
}