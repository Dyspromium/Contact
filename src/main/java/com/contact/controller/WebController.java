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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
public class WebController implements WebMvcConfigurer {

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping("/endsession")
    public String endSession(SessionStatus status){
        status.setComplete();
        return "lastpage";
    }


    Contact selected;

    @GetMapping("/home")
    public String home(Model model,Long id) {
        Iterable<Contact> all = contactRepository.findAll();
        model.addAttribute("contacts", all);

        if(id != null){
            selected = contactRepository.findById(id).get();

        }

        model.addAttribute("selected", selected);
        return "home";
    }

    @RequestMapping(value="/delete", method=RequestMethod.GET)
    public String delete(@RequestParam String action, Model m) {

        contactRepository.deleteById(selected.getId());
        selected = null;
        return "redirect:/home";
    }

    @GetMapping("/add")
    public String add(Model model, Contact contact) {
        model.addAttribute("contact", contact);
        return "add";
    }
    @PostMapping("/add")
    public String addSubmit(@ModelAttribute Contact contact, BindingResult result, Model model) {
        if (Objects.equals(contact.getName(), "")) {
            return add(model, contact);
        }
        if(contactRepository.findByMail(contact.getTrymail()).isEmpty()){
            contactRepository.save(contact);
            return "redirect:/home";
        }
        return add(model, contact);
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