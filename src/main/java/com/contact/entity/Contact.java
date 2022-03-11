package com.contact.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@Entity
@XmlRootElement
@Table(name = "contact")
public class Contact{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;
    private String trymail;

    @ElementCollection
    private List<String> mail;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User user;

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

    public void setId(long id) {
        this.id = id;
    }

    public void setMail(List<String> mail) {
        this.mail = mail;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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