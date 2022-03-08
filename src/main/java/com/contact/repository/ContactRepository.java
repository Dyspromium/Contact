package com.contact.repository;


import com.contact.entity.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ContactRepository extends CrudRepository<Contact, Long> {
    public List<Contact> findByNameLike(String name);

    @Query("select c from Contact c WHERE :mail in elements(c.mail)")
    List<Contact> findByMail(@Param("mail") String mail);

    public Contact findById(int id);
}