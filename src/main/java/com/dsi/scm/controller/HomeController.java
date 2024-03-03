package com.dsi.scm.controller;

import com.dsi.scm.dao.UserRepository;
import com.dsi.scm.model.User;
import com.dsi.scm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class HomeController {

    @ModelAttribute
    public Principal getPrincipal(Principal principal) {
        System.out.println("\n\n" + principal + "\n\n");
        return principal;
    }

    private final UserService userService;

    public HomeController (UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/")
    public String index(Model model){
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model){
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
//            System.out.println(result);
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
    public String login(Authentication auth, HttpServletRequest request){
        if (auth != null && auth.isAuthenticated()) {

            return "redirect:/user/dashboard";

        }

        return "login";
    }




}
