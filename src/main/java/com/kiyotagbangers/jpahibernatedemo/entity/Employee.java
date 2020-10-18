package com.kiyotagbangers.jpahibernatedemo.entity;

import javax.persistence.*;

@Entity
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Default strategy.All subclasses are mapped to one table
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  // Individual table will be created for per concrete entity class
@Inheritance(strategy = InheritanceType.JOINED)
// @DiscriminatorColumn(name = "EmployeeType")
public abstract class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    public Employee() {
    }

    public Employee(String name) {
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
        return "Employee{" +
                "name='" + name + '\'' +
                '}';
    }
}
