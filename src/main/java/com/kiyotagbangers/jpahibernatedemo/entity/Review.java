package com.kiyotagbangers.jpahibernatedemo.entity;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String rating;

    private String description;

    // default fetch strategy is EAGER
    // Many of the reviews are associated with one course
    @ManyToOne
    private Course course;

    public Review() {
    }

    public Review(String rating,String description) {
        this.rating = rating;
        this.description = description;
    }

    public String getName() {
        return description;
    }

    public void setName(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Review{" +
                "rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                '}';
        // return String.format("Review[%s %s]", rating, description);
    }
}
