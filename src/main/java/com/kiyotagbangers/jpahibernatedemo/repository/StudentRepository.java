package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import com.kiyotagbangers.jpahibernatedemo.entity.Passport;
import com.kiyotagbangers.jpahibernatedemo.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class StudentRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    public Student findById(Long id){
        return em.find(Student.class, id);
    }

    public Student save(Student course){
        if(course.getId() == null){
            em.persist(course);
        } else {
            em.merge(course);
        }
        return course;
    }

    public void deleteById(Long id){
        Student course = findById(id);

        // If you are trying to make a change in data, you should do it within something called a transaction
        em.remove(course);
    }

    public void saveStudentWithPassport(){
        logger.info("studentWithPassportEntityManager - start");

        Passport passport = new Passport("A123456");

        // if you comment-out under one line, "unsaved transient instance" error occur
        em.persist(passport);

        Student student = new Student("Taro");
        student.setPassport(passport);
        em.persist(student);
    }

    public void someOperationToUnderstandPersistenceContext() {
        // Retrieve student
        Student student = em.find(Student.class, 20001L);

        // Retrieve passport
        Passport passport = student.getPassport();

        // update passport
        passport.setNumber("E123456");

        // update student
        student.setName("Yamada - updated");

        // Persistence Context is killed as the transaction is ended
    }

    public void insertHardcodedStudentAndCourse(){
        Student student = new Student("Tanakayama");
        Course course = new Course("Microservices");
        em.persist(student);
        em.persist(course);
        student.addCourses(course);
        course.addStudent(student);

        // owning side
        em.persist(student);
    }
    public void insertStudentAndCourse(Student student, Course course){
        student.addCourses(course);
        course.addStudent(student);
        em.persist(student);
        em.persist(course);
    }
}
