package com.dsi.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @ModelAttribute
    public Principal getPrincipal(Principal principal) {
        System.out.println("\n\n" + principal + "\n\n");
        return principal;
    }

    @GetMapping("/about")
    public String getAbout() {
        return "/user/about";
    }

    @GetMapping("/dashboard")
    public String userDashboard(Model model) {
        return "user/dashboard";
    }

    @GetMapping("/contacts")
    public String viewContacts(){
        return "user/contacts";
    }

    @GetMapping("/add-contact")
    public String addContact(){
        return "user/addContact";
    }
}
