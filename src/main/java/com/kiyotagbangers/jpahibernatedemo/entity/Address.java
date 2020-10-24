package com.kiyotagbangers.jpahibernatedemo.entity;

import javax.persistence.Embeddable;

@Embeddable // embedded in other entities
public class Address {

    private String line1;
    private String line2;
    private String city;

    public Address() {
    }

    public Address(String line1, String line2, String city) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
    }
}
