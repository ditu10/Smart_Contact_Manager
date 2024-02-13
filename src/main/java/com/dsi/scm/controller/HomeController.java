package com.dsi.scm.controller;

import com.dsi.scm.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
    @GetMapping("/signup")
    public String signup(Model model) {
        if(!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }

        if(model.containsAttribute("user")){
            System.out.println(model.getAttribute("user"));
        }
        return "signup";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
