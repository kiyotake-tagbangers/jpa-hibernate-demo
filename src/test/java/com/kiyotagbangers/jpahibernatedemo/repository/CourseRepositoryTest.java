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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class CourseRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseRepository repository;

    @Autowired
    EntityManager em;

    @Test
    public void findById_basic() {
        // logger.info("Testing is Running");
        Course course = repository.findById(10002L);
        assertEquals("Spring practice", course.getName());
    }

    @Test
    @DirtiesContext
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
    @DirtiesContext
    void playWithEntityManager() {
        repository.playWithEntityManager();
    }

    @Test
    @Transactional
    void retrieveReviewsForCourse() {
        Course course = repository.findById(10001L);
        // EAGER fetch
        // select
        //    course0_.id as id1_0_0_,
        //    course0_.created_date as created_2_0_0_,
        //    course0_.last_updated_date as last_upd3_0_0_,
        //    course0_.name as name4_0_0_,
        //    reviews1_.course_id as course_i4_2_1_,
        //    reviews1_.id as id1_2_1_,
        //    reviews1_.id as id1_2_2_,
        //    reviews1_.course_id as course_i4_2_2_,
        //    reviews1_.description as descript2_2_2_,
        //    reviews1_.rating as rating3_2_2_
        //from
        //    course course0_
        //left outer join
        //    review reviews1_
        //        on course0_.id=reviews1_.course_id
        //where
        //    course0_.id=?select
        //    course0_.id as id1_0_0_,
        //    course0_.created_date as created_2_0_0_,
        //    course0_.last_updated_date as last_upd3_0_0_,
        //    course0_.name as name4_0_0_,
        //    reviews1_.course_id as course_i4_2_1_,
        //    reviews1_.id as id1_2_1_,
        //    reviews1_.id as id1_2_2_,
        //    reviews1_.course_id as course_i4_2_2_,
        //    reviews1_.description as descript2_2_2_,
        //    reviews1_.rating as rating3_2_2_
        //from
        //    course course0_
        //left outer join
        //    review reviews1_
        //        on course0_.id=reviews1_.course_id
        //where
        //    course0_.id=?

        // LAZY fetch (@OneToMany default behavior)
        //     select
        //        course0_.id as id1_0_0_,
        //        course0_.created_date as created_2_0_0_,
        //        course0_.last_updated_date as last_upd3_0_0_,
        //        course0_.name as name4_0_0_
        //    from
        //        course course0_
        //    where
        //        course0_.id=?

        // if there is no @Transactional, LazyInitializationException occur
        // failed to lazily initialize a collection of role: com.kiyotagbangers.jpahibernatedemo.entity.Course.reviews, could not initialize proxy - no Session
        // LAZY fetch (@OneToMany default behavior)
        //     select
        //        reviews0_.course_id as course_i4_2_0_,
        //        reviews0_.id as id1_2_0_,
        //        reviews0_.id as id1_2_1_,
        //        reviews0_.course_id as course_i4_2_1_,
        //        reviews0_.description as descript2_2_1_,
        //        reviews0_.rating as rating3_2_1_
        //    from
        //        review reviews0_
        //    where
        //        reviews0_.course_id=?
        logger.info("{}", course.getReviews());
    }

    @Test
    @Transactional
    void retrieveCourseForReview() {
        Review review = em.find(Review.class, 40001L);
        // EAGER fetch(@ManyToOne default behavior)
        //     select
        //        review0_.id as id1_2_0_,
        //        review0_.course_id as course_i4_2_0_,
        //        review0_.description as descript2_2_0_,
        //        review0_.rating as rating3_2_0_,
        //        course1_.id as id1_0_1_,
        //        course1_.created_date as created_2_0_1_,
        //        course1_.last_updated_date as last_upd3_0_1_,
        //        course1_.name as name4_0_1_
        //    from
        //        review review0_
        //    left outer join
        //        course course1_
        //            on review0_.course_id=course1_.id
        //    where
        //        review0_.id=?
        logger.info("{}", review.getCourse());
    }

    /**
     * First level cached is the boundary of the transaction
     */
    @Test
    @Transactional // if this comment-out select query execute 2 times
    void findById_firstLevelCacheDemo() {
        Course course = repository.findById(10001L);
        logger.info("First Course Retrieved {}", course);

        Course course1 = repository.findById(10001L);
        logger.info("First Course Retrieved again {}", course1);

        // 2020-10-24 18:18:16.060  INFO 2622 --- [           main] c.k.j.repository.CourseRepositoryTest    : First Course Retrieved Course{name='JPA exercise'}
        // You don't see any query because of within the scope of transaction and within a persistence context the data for the entities is cached
        //2020-10-24 18:18:16.063  INFO 2622 --- [           main] c.k.j.repository.CourseRepositoryTest    : First Course Retrieved again Course{name='JPA exercise'}

        assertEquals("JPA exercise", course.getName());
        assertEquals("JPA exercise", course1.getName());
    }
}