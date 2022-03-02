package com.entity.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Controller
public class WebController {

    @RequestMapping("/endsession")
    public String endSession(SessionStatus status){
        status.setComplete();
        return "lastpage";
    }

}