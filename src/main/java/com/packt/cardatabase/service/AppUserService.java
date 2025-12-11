package com.packt.cardatabase.service;

import com.packt.cardatabase.domain.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    private AppUserRepository userRepository;
    
    // Setter injection
    @Autowired
    public void setAppUserRepository(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void saveUser(String username) {
        System.out.println("Saving user: " + username);
    }
}




