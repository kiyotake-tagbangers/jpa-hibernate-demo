package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.JpaHibernateDemoApplication;
import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import com.kiyotagbangers.jpahibernatedemo.entity.Review;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class CourseSpringDataRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseSpringDataRepository repository;
    
    @Test
    public void findById_CoursePresent(){
        Optional<Course> courseOptional = repository.findById(10001L);
        assertTrue(courseOptional.isPresent());
    }

    @Test
    public void findById_CourseNotPresent(){
        Optional<Course> courseOptional = repository.findById(20001L);
        assertFalse(courseOptional.isPresent());
    }

    @Test
    void playingAroundWithSpringDataRepository() {
        Course course = new Course("Microservices practice");
        repository.save(course);
        //     insert
        //    into
        //        course
        //        (created_date, last_updated_date, name, id)
        //    values
        //        (?, ?, ?, ?)

        course.setName("Microservices practice - Updated");
        repository.save(course);
        //     select
        //        course0_.id as id1_0_0_,
        //        course0_.created_date as created_2_0_0_,
        //        course0_.last_updated_date as last_upd3_0_0_,
        //        course0_.name as name4_0_0_
        //    from
        //        course course0_
        //    where
        //        course0_.id=?

        //     update
        //        course
        //    set
        //        created_date=?,
        //        last_updated_date=?,
        //        name=?
        //    where
        //        id=?
        logger.info("Courses -> {}", repository.findAll());
        // Courses -> [Course{name='Microservices practice - Updated'}, Course{name='JPA exercise'}, Course{name='Spring practice'}, Course{name='Spring Boot practice'}]

        logger.info("Count -> {}", repository.count());
        // Count -> 4
    }
}