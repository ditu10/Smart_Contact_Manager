package com.dsi.scm.service;


import com.dsi.scm.dao.ContactRepository;
import com.dsi.scm.model.Contact;
import com.dsi.scm.model.User;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact getContactByUserAndContactId(User user, int contactId){
        return contactRepository.getContactByUserAndId(user, contactId);
    }
}
