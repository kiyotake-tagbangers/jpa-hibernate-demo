package com.kiyotagbangers.jpahibernatedemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "CourseDetails")
@NamedQueries(
        value = {
                @NamedQuery(name = "query_get_all_courses", query = "Select c from Course c"),
                @NamedQuery(name = "query_get_all_updated_courses", query = "Select c from Course c where name like '% Updated'"),
                @NamedQuery(name = "query_get_practice_courses", query = "Select c From Course c where name like '% practice'")
        }
)
@Cacheable
@SQLDelete(sql = "update course set is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    // @Column(name = "fullname", nullable = false)
    private String name;

    // default fetch strategy is LAZY
    // mappedBy use non-own side entity
    // @ManyToOne field name(owns the relationship) specify
    @OneToMany(mappedBy = "course")
    private List<Review> reviews;

    // default fetch strategy is LAZY
    // student_courses would become the join table between the student and the course
    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    private boolean isDeleted;

    // default no args constructor
    protected Course() {
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReviews(Review reviews) {
        this.reviews.add(reviews);
    }

    public void removeReviews(Review reviews) {
        this.reviews.remove(reviews);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                '}';
    }
}
