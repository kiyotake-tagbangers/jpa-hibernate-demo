package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.JpaHibernateDemoApplication;
import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class CourseSpringDataRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseSpringDataRepository repository;

    @Test
    public void findById_CoursePresent() {
        Optional<Course> courseOptional = repository.findById(10001L);
        assertTrue(courseOptional.isPresent());
    }

    @Test
    public void findById_CourseNotPresent() {
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
    void findUsingNameAndIdSucceed() {
        List<Course> courses = repository.findByNameAndId("JPA exercise", 10001L);
        assertThat(courses).extracting("name").contains("JPA exercise");
    }

    @Test
    void findUsingNameAndIdFailure() {
        List<Course> courses = repository.findByNameAndId("JPA exercise", 10002L);
        assertThat(courses).hasSize(0);
        //     select
        //        course0_.id as id1_0_,
        //        course0_.created_date as created_2_0_,
        //        course0_.last_updated_date as last_upd3_0_,
        //        course0_.name as name4_0_
        //    from
        //        course course0_
        //    where
        //        course0_.name=?
        //        and course0_.id=?
        //2020-10-23 08:00:03.925 TRACE 1584 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [JPA exercise]
        //2020-10-23 08:00:03.925 TRACE 1584 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [BIGINT] - [10002]
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

    @Test
    void deleteByName() {
        repository.deleteByName("Spring practice");
        List<Course> courses = repository.findAll();
        assertThat(courses).extracting("name").doesNotContain("Spring practice");
    }

    @Test
    void courseStudentsLazyInitializeException() {
        Optional<Course> courseOptional = repository.findById(10001L);
        logger.info("Course name: {}", courseOptional.get().getName());
        assertThrows(LazyInitializationException.class, () -> {
            courseOptional.get().getReviews().get(0);
        });

        // If Course class reviews field is EAGER fetch
        // logger.info("Review: {}", courseOptional.get().getReviews().get(0));
    }

    @Test
    void findByNameOrderByIdDesc() {

        Course course = new Course("Spring Boot practice");
        repository.save(course);

        List<Course> courses = repository.findByNameOrderByIdDesc("Spring Boot practice");
        Long firstElementId = courses.get(0).getId();
        Long secondElementId = courses.get(1).getId();

        assertThat(firstElementId).isGreaterThan(secondElementId);
    }
}