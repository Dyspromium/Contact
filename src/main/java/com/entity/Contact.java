package com.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Contact{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToMany
    @JoinTable(name="ADDRESSES")
    private Collection<Address> addresses;

}