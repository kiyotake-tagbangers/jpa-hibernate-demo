package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.JpaHibernateDemoApplication;
import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class CourseRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseRepository repository;

    @Test
    public void findById_basic() {
        // logger.info("Testing is Running");
        Course course = repository.findById(10002L);
        assertEquals("Spring practice", course.getName());
    }

    @Test
    void save_basic() {
        Course course = repository.findById(10002L);
        assertEquals("Spring practice", course.getName());
        course.setName("Spring practice - Updated");
        repository.save(course);
        Course course1 = repository.findById(10002L);
        assertEquals("Spring practice - Updated", course1.getName());
    }

    @Test
    @DirtiesContext // after this test is run, spring would automatically reset the date
    public void deleteById_basic() {
        repository.deleteById(10002L);
        assertNull(repository.findById(10002L));
    }

    @Test
    void playWithEntityManager() {
        repository.playWithEntityManager();
    }
}