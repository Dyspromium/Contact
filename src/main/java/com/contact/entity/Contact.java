package com.contact.entity;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Contact{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String trymail;

    @ElementCollection
    private List<String> mail;

    protected Contact() {}

    public Contact(String name) {
        this.name = name;
        this.mail = new ArrayList<String>();
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTrymail() {
        return trymail;
    }

    public void addMail(String mail){this.mail.add(mail);}


    @ManyToMany
    private Collection<Address> addresses;

}