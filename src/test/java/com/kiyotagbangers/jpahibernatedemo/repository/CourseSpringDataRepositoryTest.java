package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.JpaHibernateDemoApplication;
import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void sort() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        logger.info("Sorted Courses -> {}", repository.findAll(sort));
        //     select
        //        course0_.id as id1_0_,
        //        course0_.created_date as created_2_0_,
        //        course0_.last_updated_date as last_upd3_0_,
        //        course0_.name as name4_0_
        //    from
        //        course course0_
        //    order by
        //        course0_.name desc
        // Sorted Courses -> [Course{name='JPA exercise'}, Course{name='Spring Boot practice'}, Course{name='Spring practice'}]

        logger.info("Count -> {}", repository.count());
        // Count -> 3
    }

    @Test
    void pagination() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<Course> firstPage = repository.findAll(pageRequest);
        logger.info("First Page -> {}", firstPage.getContent());
        // First Page -> [Course{name='JPA exercise'}, Course{name='Spring practice'}, Course{name='Spring Boot practice'}]

        // Pageable secondPageable = firstPage.nextPageable();
        // Page<Course> secondPage = repository.findAll(secondPageable);
        // logger.info("Second Page -> {}", secondPage.getContent());
        // Second Page -> [Course{name='Dummy1'}, Course{name='Dummy2'}, Course{name='Dummy3'}]
    }

    @Test
    void findUsingName() {
        logger.info("FindByName -> {}", repository.findByName("JPA exercise"));
        //     select
        //        course0_.id as id1_0_,
        //        course0_.created_date as created_2_0_,
        //        course0_.last_updated_date as last_upd3_0_,
        //        course0_.name as name4_0_
        //    from
        //        course course0_
        //    where
        //        course0_.name=?
        //2020-10-22 08:41:11.104 TRACE 14321 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [JPA exercise]
        //  FindByName -> [Course{name='JPA exercise'}]
    }
}