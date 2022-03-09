package com.contact.repository;


import com.contact.entity.Address;
import com.contact.entity.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AddressRepository extends CrudRepository<Address, Long> {
    public Address findById(int id);
}