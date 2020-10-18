package com.kiyotagbangers.jpahibernatedemo.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    // default fetch strategy is LAZY
    @OneToMany(mappedBy = "course")
    private List<Review> reviews;

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

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReviews(Review reviews) {
        this.reviews.add(reviews);
    }

    public void removeReviews(Review reviews) {
        this.reviews.remove(reviews);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                '}';
    }
}
