package com.contact.repository;


import com.contact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User,Long> {
        public Optional<User> findById(Long id);
}