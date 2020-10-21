package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.JpaHibernateDemoApplication;
import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
public class CriteriaQueryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    EntityManager em;

    /**
     * Criteria API is prepared as a JPA API
     * It constructs sql dynamically
     */
    @Test
    void all_courses() {
        // 1. Use Criteria Builder to create a Criteria Query returning the expected result object
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        // 2. Define roots for tables which are involved in the query
        Root<Course> courseRoot = cq.from(Course.class);

        // 3. Define Predicates etc using Criteria Builder
        // 4. Add Predicates etc to the Criteria Query
        // 5. Build the TypedQuery using the entity manager and criteria query
        TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
        List<Course> resultList = query.getResultList();
        logger.info("Typed Query -> {}", resultList);
        // Typed Query -> [Course{name='JPA practice'}, Course{name='Spring practice'}, Course{name='Spring Boot practice'}]
    }

    @Test
    void all_courses_having_practice() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> courseRoot = cq.from(Course.class);

        Predicate likePractice = cb.like(courseRoot.get("name"), "%practice");
        cq.where(likePractice);

        TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
        List<Course> resultList = query.getResultList();
        logger.info("Typed Query -> {}", resultList);
        // Typed Query -> [Course{name='Spring practice'}, Course{name='Spring Boot practice'}
    }

    @Test
    void all_courses_without_students() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> courseRoot = cq.from(Course.class);

        Predicate studentsIsEmpty = cb.isEmpty(courseRoot.get("students"));

        cq.where(studentsIsEmpty);

        TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
        List<Course> resultList = query.getResultList();
        logger.info("Typed Query -> {}", resultList);
        // Typed Query -> [Course{name='Spring practice'}]
    }

    @Test
    void join() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> courseRoot = cq.from(Course.class);
        courseRoot.join("students");
        TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));

        List<Course> resultList = query.getResultList();
        //     select
        //        course0_.id as id1_0_,
        //        course0_.created_date as created_2_0_,
        //        course0_.last_updated_date as last_upd3_0_,
        //        course0_.name as name4_0_
        //    from
        //        course course0_
        //    inner join
        //        student_course students1_
        //            on course0_.id=students1_.course_id
        //    inner join
        //        student student2_
        //            on students1_.student_id=student2_.id

        logger.info("Typed Query -> {}", resultList);
        // Typed Query -> [Course{name='JPA exercise'}, Course{name='JPA exercise'}, Course{name='JPA exercise'}, Course{name='Spring Boot practice'}]
    }

    @Test
    void left_join() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> courseRoot = cq.from(Course.class);
        courseRoot.join("students", JoinType.LEFT);
        TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));

        List<Course> resultList = query.getResultList();
        //     select
        //        course0_.id as id1_0_,
        //        course0_.created_date as created_2_0_,
        //        course0_.last_updated_date as last_upd3_0_,
        //        course0_.name as name4_0_
        //    from
        //        course course0_
        //    left outer join
        //        student_course students1_
        //            on course0_.id=students1_.course_id
        //    left outer join
        //        student student2_
        //            on students1_.student_id=student2_.id

        logger.info("Typed Query -> {}", resultList);
        // Typed Query -> [Course{name='JPA exercise'}, Course{name='JPA exercise'}, Course{name='JPA exercise'}, Course{name='Spring practice'}, Course{name='Spring Boot practice'}]
    }
}
