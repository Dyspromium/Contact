package com.contact.controller;

import com.contact.entity.Contact;
import com.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController implements WebMvcConfigurer {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping(value="/get/{id}")
    public Contact getContact(@PathVariable String id) {
        Optional<Contact> c = contactRepository.findById((long)Integer.parseInt(id));
        return c.orElse(null);
    }

    @GetMapping("/delete/{id}")
    public void deleteCourse(@PathVariable String id)
    {
        System.out.println(id);
        contactRepository.deleteById((long)Integer.parseInt(id));
    }

    @GetMapping(value="/get/all")
    public List<Contact> getAllContacts()  {
        return (List<Contact>) contactRepository.findAll();
    }

    @PostMapping("/create")
    public String createTutorial(@RequestBody Contact contact) {
        contactRepository.save(new Contact(contact.getName()));
        return "redirect:/home";

    }
}