package com.kiyotagbangers.jpahibernatedemo.repository;

import com.kiyotagbangers.jpahibernatedemo.entity.Course;
import com.kiyotagbangers.jpahibernatedemo.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import java.util.List;

@Repository
@Transactional
public class EmployeeRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    // insert an employee
    public void insert(Employee employee){
        em.persist(employee);
    }

    // comment-out when @MappedSuperclass uses
    // retrieve all employees
    // public List<Employee> retrieveAllEmployees(){
        // return em.createQuery("select e from Employee e", Employee.class).getResultList();
    // }

    public List<Employee> retrieveAllPartTimeEmployees(){
        return em.createQuery("select e from PartTimeEmployee e", Employee.class).getResultList();
    }

    public List<Employee> retrieveFullPartTimeEmployees(){
        return em.createQuery("select e from FullTimeEmployee e", Employee.class).getResultList();
    }

    public Course findById(Long id){
        return em.find(Course.class, id);
    }
}
