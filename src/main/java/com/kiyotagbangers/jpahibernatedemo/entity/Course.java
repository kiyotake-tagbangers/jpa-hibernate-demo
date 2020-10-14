package com.kiyotagbangers.jpahibernatedemo.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
//@Table(name = "CourseDetails")
@NamedQueries(
        value = {
                @NamedQuery(name = "query_get_all_courses", query = "Select c from Course c"),
                @NamedQuery(name = "query_get_all_updated_courses", query = "Select c from Course c where name like '% Updated'")
        }
)
public class Course {

    @Id
    @GeneratedValue
    private Long id;

//    @Column(name = "fullname", nullable = false)
    private String name;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;


    // default no args constructor
    protected Course(){
    }

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                '}';
    }
}
