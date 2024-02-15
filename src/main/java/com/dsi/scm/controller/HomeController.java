package com.dsi.scm.controller;

import com.dsi.scm.dao.UserRepository;
import com.dsi.scm.model.User;
import com.dsi.scm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController (UserService userService) {
        this.userService = userService;
    }



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
            userService.save(user);
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

    @GetMapping("/login")
    public String login(){
        return "login";
    }




}
