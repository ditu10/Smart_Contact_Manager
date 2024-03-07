package com.dsi.scm.dao;

import com.dsi.scm.model.Contact;
import com.dsi.scm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact , Integer> {

    public Page<Contact> getContactsByUser(User user, Pageable pageable);

    Contact getContactByUserAndId(User user, int contactId);

    List<Contact> getContactsByNameContainingAndUser(String keyword, User user);
}
