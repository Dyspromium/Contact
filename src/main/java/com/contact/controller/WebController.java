package com.contact.controller;

import com.contact.entity.Address;
import com.contact.repository.AddressRepository;
import com.contact.repository.ContactRepository;
import com.contact.entity.Contact;
import com.contact.entity.User;
import com.contact.repository.UserRepository;
import com.sun.jdi.event.ExceptionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;
import javax.servlet.http.HttpSession;


@Controller
public class WebController implements WebMvcConfigurer {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/endsession")
    public String endSession(HttpSession session) {
        session.invalidate();
        selected = null;
        return "redirect:/home";
    }

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/home";
    }


    Contact selected;
    String messageError;

    @GetMapping("/home")
    public String home(Model model, Long id, HttpSession session) {

        if (session.getAttribute("valueSessionId") == null) {
            System.out.println("SESSION" + session);
            System.out.println("Session Id" + session.getAttribute("valueSessionId"));
            return "redirect:/login";
        } else {
            Iterable<Contact> all = contactRepository.findByUserId((Long) session.getAttribute("valueSessionId"));
            all.forEach(contact -> System.out.println(contact.getUser()));

            model.addAttribute("contacts", all);

            if (id != null) {
                if (contactRepository.findById(id).isEmpty()) {
                    selected = null;
                } else {
                    selected = contactRepository.findById(id).get();
                }
            }

            model.addAttribute("selected", selected);
            return "home";
        }
    }

    @GetMapping("/delete/mail")
    public String deleteMail(Model model, String mail, HttpSession session) {

        if (session.getAttribute("valueSessionId") == null) {
            return "redirect:/login";
        } else {
            if (mail != null) {
                selected.deleteMail(mail);
                contactRepository.save(selected);
            }
            return "redirect:/" + home(model, selected.getId(),session);
        }
    }

    @GetMapping("/delete/address")
    public String deleteMail(Model model, Integer id, HttpSession session) {

        if (session.getAttribute("valueSessionId") == null) {
            return "redirect:/login";
        } else {

            if (id != null) {
                Optional<Address> add = Optional.ofNullable(addressRepository.findById(id));
                if (add.isPresent()) {
                    selected.deleteAddress(add.get());
                    contactRepository.save(selected);
                }
            }
            return "redirect:/" + home(model, selected.getId(),session);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam String action, Model m) {

        contactRepository.deleteById(selected.getId());
        selected = null;
        return "redirect:/home";
    }

    @GetMapping("/add")
    public String add(Model model, Contact contact, Address address, HttpSession session) {
        if (session.getAttribute("valueSessionId") == null) {
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
            messageError = "Nom nul !";
            model.addAttribute("messageError", messageError);
            return "add";
        }

        if (contactRepository.findByMail(contact.getTrymail()).isEmpty()) {
            address.setContact(contact);
            List<Address> test = contact.getAddresses();
            test.add(address);

            contact.addMail(contact.getTrymail());
            Optional<User> user = userRepository.findById((Long) session.getAttribute("valueSessionId"));

            if(user.isPresent()){
                contact.setUser(user.get());
                List<Contact> contacts = user.get().getContact();
                contacts.add(contact);
            }
            contactRepository.save(contact);
            messageError = "";
            return "redirect:/" + home(model, contact.getId(),session);
        }
        messageError = "Mail d??j?? utilis?? !";
        model.addAttribute("messageError", messageError);
        return "add";
    }

    @GetMapping("/add/mail")
    public String addMail(Model model, HttpSession session) {
        if (session.getAttribute("valueSessionId") == null) {
            messageError = "";
            return "redirect:/login";
        } else {
            selected.setTrymail("");
            messageError = "Email already used";
            model.addAttribute("messageError", messageError);
            model.addAttribute("selected", selected);
            return "addMail";
        }
    }

    @PostMapping("/add/mail")
    public String addMailSubmit(@ModelAttribute Contact contact, BindingResult result,Model model) {
        if (Objects.equals(contact.getName(), "")) {
            model.addAttribute("messageError", messageError);
            return "redirect:/add/mail";
        }
        if (contactRepository.findByMail(contact.getTrymail()).isEmpty()) {
            selected.addMail(contact.getTrymail());
            contactRepository.save(selected);
            selected = contactRepository.findById(selected.getId()).get();
            return "redirect:/home";
        }

        model.addAttribute("messageError", messageError);
        return "redirect:/add/mail";
    }

    @GetMapping("/add/address")
    public String addAddress(Model model, Address address, HttpSession session) {
        if (session.getAttribute("valueSessionId") == null) {
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
        selected = contactRepository.findById(selected.getId()).get();
        return "redirect:/home";
    }

    @PostMapping("/deleteAllUser")
    public String deleteAllUser() {
        userRepository.deleteAll();
        return "redirect:/adminPanel";
    }


    @GetMapping("/login")
    public String login(Model model, User user) {
        model.addAttribute("users", user);
        model.addAttribute("messageError", messageError);
        return "login";
    }

    @PostMapping("/login")
    //TODO  VERIF SESSION  + AFFICHAGE DE LA SESSION SUR CONTACT + VERIFIER JOINTURE ENTRE USER ET CONTACT
    public String loginSubmit(User user, HttpSession session,Model model) {
        messageError = "Invalid Credential";
        if (Objects.equals(user.getLogin(), "admin") && Objects.equals(user.getPassword(), "admin")) {
            session.setAttribute("valueSessionName", user.getLogin());
            session.setAttribute("valueSessionId", user.getId());
            session.setAttribute("role", "admin");
            messageError = "";
            return "redirect:/home";
        } else if (userRepository.findUserByLogin(user.getLogin()) != null) {
            User dbUser = userRepository.findUserByLogin(user.getLogin());

            if (Objects.equals(dbUser.getLogin(), user.getLogin()) && Objects.equals(user.getPassword(), dbUser.getPassword())) {
                session.setAttribute("valueSessionName", dbUser.getLogin());
                session.setAttribute("valueSessionId", dbUser.getId());
                messageError = "";
                return "redirect:/home";
            } else {
                model.addAttribute("messageError", messageError);

                return "redirect:/login";
            }
        } else {
            model.addAttribute("messageError", messageError);
            return "redirect:/login";
        }

    }


    @GetMapping("/adminPanel")
    public String adminPanel(Model model, User user, HttpSession session) {
        if (session.getAttribute("valueSessionId") == null) {
            messageError = "";
            return "redirect:/login";
        } else {
            System.out.println("Session :" + session.getAttribute("valueSessionId"));
            model.addAttribute("user", user);

            return "/adminPanel";
        }
    }

    @PostMapping("/adminPanel")
    public String adminSubmit(Model model, User user) {


        if (userRepository.findUserByLogin(user.getLogin()) == null) {
            userRepository.save(user);
            messageError = "Utilisateur ajout?? !";
            model.addAttribute("messageError", messageError);
            return "/home";
        } else {
            messageError = "Utilisateur existant !";
            model.addAttribute("messageError", messageError);
            return "/adminPanel";
        }
    }

}
