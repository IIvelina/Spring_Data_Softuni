package org.lab.data.service.impl;

import org.lab.data.entites.User;
import org.lab.data.repositories.UserRepository;
import org.lab.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void register(User user) {
        this.userRepository.saveAndFlush(user);
    }
}
