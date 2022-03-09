package com.contact.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Address{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String street;
    private String city;
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String toString(){
        return this.street + ", " + this.city + ", "+ this.country;
    }

    @ManyToOne
    @JoinColumn(name = "contact_fk")
    private Contact contact;

}

