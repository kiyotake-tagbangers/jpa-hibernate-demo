package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// @Repository
@RepositoryRestResource(path = "courses")
@Transactional
public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {

    List<Course> findByNameAndId(String name, Long id);

    List<Course> findByName(String name);

    List<Course> findByNameOrderByIdDesc(String name);

    void deleteByName(String name);

    @Query("Select c From Course c where name like '% practice'")
    List<Course> courseWithPracticeInName();

    @Query(value = "Select * From Course c where name like '% practice'",
            nativeQuery = true)
    List<Course> courseWithPracticeInNameUsingNativeQuery();

    @Query(name = "query_get_practice_courses")
    List<Course> courseWithPracticeInNameUsingNamedQuery();
}
