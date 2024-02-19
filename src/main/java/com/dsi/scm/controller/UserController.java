package com.dsi.scm.controller;

import com.dsi.scm.dao.UserRepository;
import com.dsi.scm.model.Contact;
import com.dsi.scm.model.User;
import com.dsi.scm.service.UserService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public Principal getPrincipal(Principal principal) {
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
    public String viewContacts(Principal principal, Model model) {
        List<Contact> contacts = userService.getUserByUserName(principal.getName()).getContacts();
        model.addAttribute("contacts", contacts);
        return "user/contacts";
    }

    @GetMapping("/add-contact")
    public String addContact(Model model){
        model.addAttribute("contact", new Contact());
        return "user/addContact";
    }

    @PostMapping("/process-contact")
    @ResponseBody
    public String processContact(@ModelAttribute Contact contact,
                                 @RequestParam("image") MultipartFile image,
                                 Principal principal) {
        try {
            User user = userService.getUserByUserName(principal.getName());

            if(image.isEmpty()){
                // validation
                System.out.println("Image file is empty!\n");
            }else{
                String imageurl = image.getOriginalFilename() + "-" + principal.getName() + "-" + image.getSize();
                contact.setImageUrl(imageurl);
                System.out.println("ImageURL: " + imageurl);
                System.out.println("---------------------");
                File savedFile = new ClassPathResource("static/images").getFile();
                Path path = Paths.get(savedFile+File.separator+imageurl);
                System.out.println("Path: " + path);
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println();

            }

            userService.updateUserWithContact(user,contact);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return contact.toString();
    }
}
