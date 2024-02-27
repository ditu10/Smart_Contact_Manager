package com.dsi.scm.service;

import com.dsi.scm.dao.UserRepository;
import com.dsi.scm.model.Contact;
import com.dsi.scm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void setUserOfContact(User user, Contact contact){
        contact.setUser(user);
        user.getContacts().add(contact);
    }


    public void addAdditionalFieldsWithDefaultValues(User user){
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User getUserByUserName(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }
}
