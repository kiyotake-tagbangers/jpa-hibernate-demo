package com.kiyotagbangers.jpahibernatedemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Passport {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String number;

    public Passport() {
    }

    public Passport(String number) {
        this.number = number;
    }

    public String getName() {
        return number;
    }

    public void setName(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "number='" + number + '\'' +
                '}';
    }
}
