package com.dsi.scm.controller;

import com.dsi.scm.dao.ContactRepository;
import com.dsi.scm.dao.UserRepository;
import com.dsi.scm.model.Contact;
import com.dsi.scm.model.User;
import com.dsi.scm.service.UserService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final UserService userService;
    private final ContactRepository contactRepository;
    private final int pageSize;

    public UserController(UserService userService, ContactRepository contactRepository) {
        this.userService = userService;
        this.contactRepository = contactRepository;
        this.pageSize = 3;
    }

    @ModelAttribute
    public Principal getPrincipal(Principal principal) {
        return principal;
    }
    @ModelAttribute
    public User addCommonAttribute(Principal principal) {
        return userService.getUserByUserName(principal.getName());
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
    public String viewContacts(Model model,
                               @RequestParam int page) {
        User user = (User) model.getAttribute("user");
        if(user == null){
            return "redirect:/login";
        }
        Pageable pageable = PageRequest.of(page-1,pageSize);
        Page<Contact> contacts = contactRepository.getContactsByUser(user, pageable);

        model.addAttribute("contacts", contacts);
        model.addAttribute("currentPage", page);
        System.out.println("/n totalPage = " + contacts.getTotalPages() + "/n");
        model.addAttribute("totalPage", contacts.getTotalPages());
        System.out.println();
        return "user/contacts";
    }

    @GetMapping("/add-contact")
    public String addContact(Model model){
        model.addAttribute("contact", new Contact());

        return "user/addContact";
    }

    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact,
                                 @RequestParam MultipartFile image,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserByUserName(principal.getName());

            if(image.isEmpty()){
                // validation
                contact.setImageUrl("profile.jpg");
            }else{
                String imageUrl = principal.getName() + "-" + image.getSize()+ "-" + image.getOriginalFilename();
                contact.setImageUrl(imageUrl);
                File savedFile = new ClassPathResource("static/images").getFile();
                Path path = Paths.get(savedFile+File.separator+imageUrl);
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            }

            userService.updateUserWithContact(user,contact);
            redirectAttributes.addFlashAttribute("message", "contact added successfully!! Add more...");
            redirectAttributes.addFlashAttribute("type", "success");
            return "redirect:/user/add-contact";
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/user/add-contact";
    }
}
