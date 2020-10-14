package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.JpaHibernateDemoApplication;
import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = JpaHibernateDemoApplication.class)
class JPQLTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    @Test
    public void jpql_basic() {
        Query query = em.createQuery("Select c from Course c");
        List resultList = query.getResultList();
        logger.info("Select c from Course c -> {}", resultList);
    }

    @Test
    public void jpql_typed() {
        // Type queries are always better to make it easy to read your program
        TypedQuery<Course> query = em.createQuery("Select c from Course c", Course.class);

        // It makes very clear what results you are expecting back
        List<Course> resultList = query.getResultList();
        logger.info("Select c from Course c -> {}", resultList);
    }

    @Test
    public void jpql_where() {
        // Type queries are always better to make it easy to read your program
        TypedQuery<Course> query = em.createQuery("Select c from Course c where name like '% Updated'", Course.class);

        // It makes very clear what results you are expecting back
        List<Course> resultList = query.getResultList();
        logger.info("Select c from Course c where name like '% Updated' -> {}", resultList);
    }
}