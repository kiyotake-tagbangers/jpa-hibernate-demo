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

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class PerformanceTuningTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseRepository repository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    public void courseNPlusOneProblem() {
        List<Course> courses = em.createNamedQuery("query_get_all_courses", Course.class).getResultList();

        courses.forEach(course ->
                        logger.info("Course -> {} Students -> {}", course, course.getStudents())
                // course has 3 students
                // student select statement executed 3 times
                // 24954 nanoseconds spent acquiring 1 JDBC connections;
                // 0 nanoseconds spent releasing 0 JDBC connections;
                // 2905949 nanoseconds spent preparing 4 JDBC statements;
                // 874587 nanoseconds spent executing 4 JDBC statements;
                // 0 nanoseconds spent executing 0 JDBC batches;
                // 5207544 nanoseconds spent performing 3 L2C puts;
        );
    }

    @Test
    @Transactional
    public void solvingNPlusOneProblem_EntityGraph() {
        EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
        Subgraph<Object> subGraph = entityGraph.addSubgraph("students");

        List<Course> courses = em.createNamedQuery("query_get_all_courses", Course.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        courses.forEach(course ->
                        logger.info("Course -> {} Students -> {}", course, course.getStudents())
                // 20212 nanoseconds spent acquiring 1 JDBC connections;
                // 0 nanoseconds spent releasing 0 JDBC connections;
                // 2280869 nanoseconds spent preparing 1 JDBC statements;
                // 508577 nanoseconds spent executing 1 JDBC statements;
                // 0 nanoseconds spent executing 0 JDBC batches;
                // 5186945 nanoseconds spent performing 3 L2C puts;
        );
    }

    @Test
    @Transactional
    public void solvingNPlusOneProblem_JoinFetch() {
        List<Course> courses = em.createNamedQuery("query_get_all_courses_join_fetch", Course.class).getResultList();

        courses.forEach(course ->
                        logger.info("Course -> {} Students -> {}", course, course.getStudents())
                // 26894 nanoseconds spent acquiring 1 JDBC connections;
                // 0 nanoseconds spent releasing 0 JDBC connections;
                // 3629435 nanoseconds spent preparing 1 JDBC statements;
                // 777234 nanoseconds spent executing 1 JDBC statements;
                // 0 nanoseconds spent executing 0 JDBC batches;
                // 5611707 nanoseconds spent performing 2 L2C puts;
                // 0 nanoseconds spent performing 0 L2C hits;
                // 0 nanoseconds spent performing 0 L2C misses;
        );
    }
}