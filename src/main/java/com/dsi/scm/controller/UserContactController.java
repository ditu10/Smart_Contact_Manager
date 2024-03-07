package com.dsi.scm.controller;

import com.dsi.scm.dao.UserRepository;
import com.dsi.scm.model.Contact;
import com.dsi.scm.model.User;
import com.dsi.scm.service.ContactService;
import com.dsi.scm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/contact/{contactId}")
    public String showContactDetails(@PathVariable int contactId,
                                     Model model) {
        Principal principal = (Principal) model.getAttribute("principal");
        User user = userService.getUserByUserName(principal.getName());
        Contact contact = contactService.getContactByUserAndContactId(user,contactId);
        model.addAttribute("contact", contact);
        return "user/contact";
    }

    @GetMapping("/delete")
    public String deleteContact(@RequestParam int contactId) {
        Contact contact = contactService.getContactById(contactId);
        contactService.deleteContactImage(contact);
        contactService.deleteContact(contact);
        return "redirect:/user/contacts?page=1";
    }

    @GetMapping("/update-contact")
    public String updateContactForm(@RequestParam int contactId,
                                    Model model) {
        Contact contact = contactService.getContactByUserAndContactId((User) model.getAttribute("user"),contactId);
        model.addAttribute("contact", contact);
        return "user/updateForm";
    }

    @PostMapping("update-contact")
    public String updateContactHandler(@ModelAttribute Contact contact,
                                       @RequestParam MultipartFile image,
                                       Principal principal,
                                       RedirectAttributes redirectAttributes) {

        User user = userService.getUserByUserName(principal.getName());
        Contact contact1 = contactService.getContactById(contact.getId());
        try{
            if(image.isEmpty()){
                contact.setImageUrl(contact1.getImageUrl());
            }else {
                contactService.deleteContactImage(contact1);
                contactService.uploadContactImage(image,contact,principal);
            }

            contact.setUser(user);
            contactService.save(contact);

            redirectAttributes.addFlashAttribute("message", "contact updated successfully!! Add more...");
            redirectAttributes.addFlashAttribute("type", "success");
            return "redirect:/user/contact/" + contact.getId();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/user/update-contact?contactId="+contact.getId();
    }
}
