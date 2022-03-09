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

    @GetMapping("/delete/mail")
    public String deleteMail(Model model,String mail, HttpSession session) {

        if (session.getAttribute("valueSessionId") == null){
            return "redirect:/login";
        } else {
            if(mail != null){
                selected.deleteMail(mail);
                contactRepository.save(selected);
            }
            return "redirect:/home";
        }
    }

    @GetMapping("/delete/address")
    public String deleteMail(Model model,Integer id, HttpSession session) {

        if (session.getAttribute("valueSessionId") == null){
            return "redirect:/login";
        } else {

            if(id != null){
                Optional<Address> add = Optional.ofNullable(addressRepository.findById(id));
                if(add.isPresent()){
                    selected.deleteAddress(add.get());
                    contactRepository.save(selected);
                }
            }
            return "redirect:/home";
        }
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

            contact.addMail(contact.getTrymail());
            contactRepository.save(contact);
            return "redirect:/home";
        }
        return "add";
    }

    @GetMapping("/add/mail")
    public String addMail(Model model, HttpSession session) {
        if (session.getAttribute("valueSessionId") == null){
            return "redirect:/login";
        } else {
            selected.setTrymail("");
            model.addAttribute("selected", selected);
            return "addMail";
        }
    }

    @PostMapping("/add/mail")
    public String addMailSubmit(@ModelAttribute Contact contact, BindingResult result) {
        if (Objects.equals(contact.getName(), "")) {
            return "redirect:/add/mail";
        }
        if(contactRepository.findByMail(contact.getTrymail()).isEmpty()){
            selected.addMail(contact.getTrymail());
            contactRepository.save(selected);
            return "redirect:/home";
        }
        return "redirect:/add/mail";
    }

    @GetMapping("/add/address")
    public String addAddress(Model model, Address address, HttpSession session) {
        if (session.getAttribute("valueSessionId") == null){
            return "redirect:/login";
        } else {
            model.addAttribute("selected", selected);
            model.addAttribute("address", address);
            return "addAddress";
        }
    }

    @PostMapping("/add/address")
    public String addAddressSubmit(@ModelAttribute Address address, BindingResult result) {

        address.setContact(selected);
        List<Address> test = selected.getAddresses();
        test.add(address);

        contactRepository.save(selected);
        return "redirect:/home";
    }



    @GetMapping("/login")
    public String login(Model model, User user) {
        model.addAttribute("users", user);
        return "login";
    }

    @PostMapping("/login")
    //TODO  VERIF SESSION  + AFFICHAGE DE LA SESSION SUR CONTACT + VERIFIER JOINTURE ENTRE USER ET CONTACT
    public String loginSubmit(HttpSession session,User user) {
        if(Objects.equals(user.getLogin(), "admin") && Objects.equals(user.getPassword(), "admin")) {
            session.setAttribute("valueSessionName", user.getLogin());
            session.setAttribute("valueSessionId", user.getId());
            return "redirect:/home";
        }
        return "login";
    }
}