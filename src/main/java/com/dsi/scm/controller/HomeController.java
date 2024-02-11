package com.dsi.scm.controller;

import com.dsi.scm.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
