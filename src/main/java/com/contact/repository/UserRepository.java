package com.contact.repository;


import com.contact.entity.Address;
import com.contact.entity.Contact;
import com.contact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
        public User findUserByLogin(String Login);

        @Query("select u from User u WHERE :id = u.id")
        public User findById(@Param("id")long id);

}