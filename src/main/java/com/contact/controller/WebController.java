package com.contact.controller;

import com.contact.repository.ContactRepository;
import com.contact.entity.Contact;
import com.contact.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;


@Controller
public class WebController implements WebMvcConfigurer {

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping("/endsession")
    public String endSession(HttpSession session){
        session.invalidate();
        return "redirect:/home";
    }


    Contact selected = null;
    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        if (session.getAttribute("valueSessionId") == null){
            return "redirect:/login";
        }
        else {
            Iterable<Contact> all = contactRepository.findAll();
            model.addAttribute("contacts", all);
            model.addAttribute("selected", selected);
            return "home";
        }
    }

    @GetMapping("/add")
    public String add(Model model, Contact contact) {
        model.addAttribute("contact", contact);
        return "add";
    }
    @PostMapping("/add")
    public String addSubmit(@ModelAttribute Contact contact, BindingResult result, Model model, HttpSession session) {
        if(contactRepository.findByMail(contact.getTrymail()).isEmpty()){
            contactRepository.save(contact);
            return home(model, session);
        }
        return "add";
    }



    @GetMapping("/login")
    public String login(Model model, User user) {
        //if pas de users
        model.addAttribute("users", user);
        //sinon vérif connexion

        return "login";
    }
    @PostMapping("/login")
    //TODO  VERIF SESSION  + AFFICHAGE DE LA SESSION SUR CONTACT + VERIFIER JOINTURE ENTRE USER ET CONTACT
    public String loginSubmit(HttpSession session,User user) {
        session.setAttribute("valueSessionName", user.getLogin());
        session.setAttribute("valueSessionId", user.getId());
        return "redirect:/home";
    }
}