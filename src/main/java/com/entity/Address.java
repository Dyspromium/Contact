package com.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Address{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String street;

    private String country;

    @ManyToMany(mappedBy = "adresses")
    private Collection<Contact> contacts;

}

