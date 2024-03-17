package com.example.softunigamestore.services;

import com.example.softunigamestore.entities.users.LoginDTO;
import com.example.softunigamestore.entities.users.RegisterDTO;
import com.example.softunigamestore.entities.users.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    User register(RegisterDTO registerData);
    void login();
    void logout();
}
