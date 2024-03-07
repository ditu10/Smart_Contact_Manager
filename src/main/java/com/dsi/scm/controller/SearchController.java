package com.dsi.scm.controller;

import com.dsi.scm.dao.ContactRepository;
import com.dsi.scm.dao.UserRepository;
import com.dsi.scm.model.Contact;
import com.dsi.scm.model.User;
import com.dsi.scm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {
    private final UserService userService;
    private final ContactRepository contactRepository;

    public SearchController(UserService userService, ContactRepository contactRepository) {
        this.userService = userService;
        this.contactRepository = contactRepository;
    }


    @GetMapping("/user/search")
    public ResponseEntity<?> searchResult(@RequestParam String query,
                                               Principal principal){

        User user = userService.getUserByUserName(principal.getName());
        List<Contact> contacts = contactRepository.getContactsByNameContainingAndUser(query,user);

        return ResponseEntity.ok(contacts);
    }
}
