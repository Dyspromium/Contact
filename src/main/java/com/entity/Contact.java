package com.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Contact{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    public int getId() {
        return id;
    }

    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @ManyToMany
    @JoinTable(name="ADDRESSES")
    private Collection<Address> addresses;

}