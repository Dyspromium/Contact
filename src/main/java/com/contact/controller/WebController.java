package com.contact.controller;

import com.contact.entity.Address;
import com.contact.repository.AddressRepository;
import com.contact.repository.ContactRepository;
import com.contact.entity.Contact;
import com.contact.entity.User;
import com.sun.jdi.event.ExceptionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import javax.servlet.http.HttpSession;


@Controller
public class WebController implements WebMvcConfigurer {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping("/endsession")
    public String endSession(HttpSession session){
        session.invalidate();
        return "redirect:/home";
    }


    Contact selected;

    @GetMapping("/home")
    public String home(Model model,Long id, HttpSession session) {

        if (session.getAttribute("valueSessionId") == null){
            return "redirect:/login";
        } else {
            Iterable<Contact> all = contactRepository.findAll();
            model.addAttribute("contacts", all);

            if(id != null){
                if(contactRepository.findById(id).isEmpty()){
                    selected = null;
                }else{
                    selected = contactRepository.findById(id).get();
                }
            }

            model.addAttribute("selected", selected);
            return "home";
        }
    }

    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String edit(@RequestParam String action, Model model) {
        model.addAttribute("contact", selected);
        return "edit";
    }



    @RequestMapping(value="/delete", method=RequestMethod.GET)
    public String delete(@RequestParam String action, Model m) {

        contactRepository.deleteById(selected.getId());
        selected = null;
        return "redirect:/home";
    }

    @GetMapping("/add")
    public String add(Model model, Contact contact, Address address, HttpSession session) {
        if (session.getAttribute("valueSessionId") == null){
            return "redirect:/login";
        } else {
            model.addAttribute("contact", contact);
            model.addAttribute("address", address);
            return "add";
        }
    }

    @PostMapping("/add")
    public String addSubmit(@ModelAttribute Contact contact, @ModelAttribute Address address, BindingResult result, Model model, HttpSession session) {
        if (Objects.equals(contact.getName(), "")) {
            return "add";
        }
        if(contactRepository.findByMail(contact.getTrymail()).isEmpty()){
            address.setContact(contact);
            List<Address> test = contact.getAddresses();
            test.add(address);
            System.out.println(test);

            contact.addMail(contact.getTrymail());
            contactRepository.save(contact);
            return "redirect:/home";
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