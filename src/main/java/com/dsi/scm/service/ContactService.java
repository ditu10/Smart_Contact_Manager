package com.dsi.scm.service;


import com.dsi.scm.dao.ContactRepository;
import com.dsi.scm.model.Contact;
import com.dsi.scm.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact getContactByUserAndContactId(User user, int contactId){
        return contactRepository.getContactByUserAndId(user, contactId);
    }

    public void save(Contact contact){
        contactRepository.save(contact);
    }

    public void uploadContactImage(MultipartFile image, Contact contact, Principal principal) {
        try{
            if(image.isEmpty()) {
                // validation
                contact.setImageUrl("profile.jpg");
            }else{
                String imageUrl = principal.getName() + contact.hashCode() + "-" + image.getOriginalFilename();
                contact.setImageUrl(imageUrl);
                File savedFile = new ClassPathResource("static/images").getFile();
                Path path = Paths.get(savedFile+File.separator+imageUrl);
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteContactImage(Contact contact) {
        if(contact.getImageUrl() == null){
            return;
        }

        if(contact.getImageUrl().equals("profile.jpg")){
            return;
        }

        String TARGET_DIR = "target/classes/static/images/";
        Path imagePath = Paths.get(System.getProperty("user.dir"), TARGET_DIR, contact.getImageUrl());

        try{
            Files.delete(imagePath);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public Contact getContactById(int contactId) {
        return contactRepository.findById(contactId).get();
    }
}
