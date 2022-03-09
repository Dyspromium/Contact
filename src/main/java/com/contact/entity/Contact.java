package com.contact.entity;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void setTrymail(String trymail) {
        this.trymail = trymail;
    }

    public List<String> getMail() {
        return mail;
    }

    public void addMail(String mail){
        this.mail.add(mail);
    }

    public void deleteMail(String mail){
        this.mail.remove(mail);
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void deleteAddress(Address add){
        for (int i = 0; i<addresses.size(); i++) {
            if(addresses.get(i).getId() == add.getId()){
                addresses.remove(i);
                break;
            }

        }
    }
}