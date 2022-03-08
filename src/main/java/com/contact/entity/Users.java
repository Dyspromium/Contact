package com.contact.entity;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;

public class Users {
    private String login;
    private String password;

    public Users(String login, String password) {
        this.login = login;
        this.password= password;
    }

    public String getPassword() {
        return this.password;
    }
    public String getLogin() {
        return this.login;
    }
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
