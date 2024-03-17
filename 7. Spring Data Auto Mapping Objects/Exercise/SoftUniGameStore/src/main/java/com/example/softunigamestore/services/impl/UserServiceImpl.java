package com.example.softunigamestore.services.impl;

import com.example.softunigamestore.entities.users.LoginDTO;
import com.example.softunigamestore.entities.users.RegisterDTO;
import com.example.softunigamestore.entities.users.User;
import com.example.softunigamestore.repositories.UserRepository;
import com.example.softunigamestore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
@Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterDTO registerData) {
        ModelMapper mapper = new ModelMapper();
        User userToRegister = mapper.map(registerData, User.class);

        long userCount = this.userRepository.count();
        if (userCount == 0){
            userToRegister.setAdministrator(true);
        }

        return this.userRepository.save(userToRegister);
    }

    @Override
    public void login() {
        
    }


    @Override
    public void logout() {

    }
}
