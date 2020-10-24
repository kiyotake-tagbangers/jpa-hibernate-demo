package com.kiyotagbangers.jpahibernatedemo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private Passport passport;

    // want to separate class because the class is used in other places
    // @OneToOne
    @Embedded
    private Address address;
    //     create table student (
    //       id bigint not null,
    //        city varchar(255),
    //        line1 varchar(255),
    //        line2 varchar(255),
    //        name varchar(255) not null,
    //        passport_id bigint,
    //        primary key (id)
    //    )

    @ManyToMany
    @JoinTable(name = "STUDENT_COURSE",
            joinColumns = @JoinColumn(name = "STUDENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "COURSE_ID")
    )
    private List<Course> courses = new ArrayList<>();
    //     create table student_course (
    //       student_id bigint not null,
    //        course_id bigint not null
    //    )

    //     alter table student_course
    //       add constraint FKejrkh4gv8iqgmspsanaji90ws
    //       foreign key (course_id)
    //       references course

    //     alter table student_course
    //       add constraint FKq7yw2wg9wlt2cnj480hcdn6dq
    //       foreign key (student_id)
    //       references student


    public Student() {
    }

    public Student(String name) {
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

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourses(Course courses) {
        this.courses.add(courses);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
