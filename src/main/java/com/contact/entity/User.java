package com.contact.entity;

import javax.persistence.*;
import java.util.Collection;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String login;
    private String password;
    private int role;
    protected User(){};

    public User(String login, String password) {
        this.login = login;
        this.password= password;
    }

    public String getPassword() {
        return this.password;
    }
    public String getLogin() {
        return this.login;
    }
    public int getId(){ return  this.id;}
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password= password;
    }

    public void checkUsers(String password,String login) {
        //TODO

    }

    @ManyToMany
    @JoinTable(name="CONTACT")
    private Collection<Contact> contacts;
}
