package com.dsi.scm.service;

import com.dsi.scm.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User addAdditionalFieldsWithDefaultValues(User user){
        user.setEnabled(true);
        user.setRole("USER");
        return user;
    }
}
