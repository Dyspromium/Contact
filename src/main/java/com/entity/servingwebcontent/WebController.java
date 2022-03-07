package com.entity.servingwebcontent;

import com.entity.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;


@Controller
public class WebController {

    @RequestMapping("/endsession")
    public String endSession(SessionStatus status){
        status.setComplete();
        return "lastpage";
    }


    Contact selected = null;
    @GetMapping("/contact")
    public String contact(Contact c,Model model) {

        Contact ugo = new Contact();
        ugo.setName("ugo");
        Contact jady = new Contact();
        jady.setName("jady");
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(ugo);
        contacts.add(jady);
        model.addAttribute("contacts", contacts);

        if(c.getName()!=null){
            selected = c;
        }

        model.addAttribute("selected", selected);

        return "contact";
    }

    @GetMapping("/add")
    public String add(Model model) {
        return "add";
    }
}