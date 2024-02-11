package com.dsi.scm.controller;

import com.dsi.scm.dao.UserRepository;
import com.dsi.scm.model.User;
import com.dsi.scm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {

    private UserRepository userRepository;

    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/do_register")
    public String handleRegister(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        System.out.println(result);
        if(result.hasErrors()){
            System.out.println("\n\nSomething wrong!! \n\n");
            return "signup";
        }
        User user1 = userService.addAdditionalFieldsWithDefaultValues(user);
        User savedUser = userRepository.save(user1);
        return "home";
    }
}
