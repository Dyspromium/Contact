package com.contact.controller;

import com.contact.repository.ContactRepository;
import com.contact.entity.Contact;
import com.contact.entity.Users;
import com.sun.jdi.event.ExceptionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


@Controller
public class WebController implements WebMvcConfigurer {

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping("/endsession")
    public String endSession(SessionStatus status){
        status.setComplete();
        return "lastpage";
    }


    Contact selected = null;
    @GetMapping("/home")
    public String home(Model model) {
        Iterable<Contact> all = contactRepository.findAll();
        model.addAttribute("contacts", all);

        model.addAttribute("selected", selected);
        return "home";
    }

    @GetMapping("/add")
    public String add(Model model, Contact contact) {
        model.addAttribute("contact", contact);
        return "add";
    }
    @PostMapping("/add")
    public String addSubmit(@ModelAttribute Contact contact, BindingResult result, Model model) {
        if(contactRepository.findByMail(contact.getTrymail()).isEmpty()){
            contactRepository.save(contact);
            return home(model);
        }
        return "add";
    }



    @GetMapping("/form")
    public String greetingForm(Model model,String login, String password) {
        //if pas de users
        model.addAttribute("users", new Users(login,password));
        //sinon vérif connexion

        return "form";
    }
    @PostMapping("/form")
    public String greetingSubmit(@ModelAttribute Users users, Model model) {
        model.addAttribute("users", users);
        return "result";
    }
}