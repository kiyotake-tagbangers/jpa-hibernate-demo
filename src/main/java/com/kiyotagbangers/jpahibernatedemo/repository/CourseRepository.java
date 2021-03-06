package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import com.kiyotagbangers.jpahibernatedemo.entity.Review;
import com.kiyotagbangers.jpahibernatedemo.entity.ReviewRating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class CourseRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    public Course findById(Long id){
        return em.find(Course.class, id);
    }

    public Course save(Course course){
        if(course.getId() == null){
            em.persist(course);
        } else {
            em.merge(course);
        }
        return course;
    }

    public void deleteById(Long id){
        Course course = findById(id);

        // If you are trying to make a change in data, you should do it within something called a transaction
        em.remove(course);
    }

    public void playWithEntityManager(){
        logger.info("playWithEntityManager - start");

        Course course1 = new Course("Web Services");
        em.persist(course1);
        Course course2 = new Course("JS practice");
        em.persist(course2);

        em.flush(); // INSERT statement execute

        // course2 is no longer tracked by the entity manager
        em.detach(course2);

        // it's not managing anything at this point
        // so what it changes you make are not reflected in the DB
        // following UPDATE statement didn't execute
        // em.clear();

        course1.setName("Web Services - Updated");
        // course1.setName(null); // not null field
        course2.setName("JS practice - Updated");

        // the contents of course1 alone would be refreshed and get the data from the database(UPDATE statement didn't execute)
        // SELECT statement execute
        // em.refresh(course1);

        em.flush(); // UPDATE statement execute
    }

    public void addHardcodedReviewsForCourse() {
        Course course = findById(10003L);
        logger.info("course.getReviews() -> {}", course.getReviews());

        Review review1 = new Review(ReviewRating.FIVE, "Great Hands-on");
        Review review2 = new Review(ReviewRating.FIVE, "Hotsoff");

        course.addReviews(review1);
        review1.setCourse(course);

        course.addReviews(review2);
        review2.setCourse(course);

        em.persist(review1);
        em.persist(review2);
    }
    public void addReviewsForCourse(Long courseId, List<Review> reviews) {
        Course course = findById(courseId);
        logger.info("course.getReviews() -> {}", course.getReviews());

        for (Review review:reviews) {
            course.addReviews(review);
            review.setCourse(course);
            em.persist(review);
        }
    }
}
