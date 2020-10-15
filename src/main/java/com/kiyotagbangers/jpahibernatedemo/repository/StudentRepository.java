package com.kiyotagbangers.jpahibernatedemo.repository;

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
}
