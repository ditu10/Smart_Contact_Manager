package com.dsi.scm.controller;

import com.dsi.scm.dao.UserRepository;
import com.dsi.scm.model.User;
import com.dsi.scm.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String handleRegister(@Valid @ModelAttribute User user, BindingResult result, RedirectAttributes redirectAttributes) {

        if(result.hasErrors()) {
            System.out.println(result);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);

            return "redirect:/signup";
        }

        try{
            userService.addAdditionalFieldsWithDefaultValues(user);
            userRepository.save(user);
        }catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                result.rejectValue("email", "error.duplicate_email", "This email already exists");
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
                redirectAttributes.addFlashAttribute("user", user);

            }

            return "redirect:/signup";
        }
        redirectAttributes.addFlashAttribute("success", "Congratulations, You have Successfully registered!");
        return "redirect:/home";
    }
}
