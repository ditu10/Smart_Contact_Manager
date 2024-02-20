package com.dsi.scm.controller;

import com.dsi.scm.dao.UserRepository;
import com.dsi.scm.model.Contact;
import com.dsi.scm.model.User;
import com.dsi.scm.service.ContactService;
import com.dsi.scm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserContactController {

    @ModelAttribute
    private Principal getPrincipal(Principal principal){
        return principal;
    }
    @ModelAttribute
    private User getUser(Model model){
        Principal principal = (Principal) model.getAttribute("principal");
        User user = null;
        if(principal!= null){
             user = userService.getUserByUserName(principal.getName());
        }
        return user;
    }

    private final UserService userService;
    private final ContactService contactService;
    private final UserRepository userRepository;

    public UserContactController(UserService userService, ContactService contactService, UserRepository userRepository) {
        this.userService = userService;
        this.contactService = contactService;
        this.userRepository = userRepository;
    }

    @PostMapping("/{userId}/contact/{contactId}")
    public String showContactDetails(@PathVariable int userId,
                                     @PathVariable int contactId,
                                     Model model) {
        User user = userRepository.findUserById(userId);
        Contact contact = contactService.getContactByUserAndContactId(user,contactId);
        model.addAttribute("contact", contact);
        return "user/contact";
    }
}
