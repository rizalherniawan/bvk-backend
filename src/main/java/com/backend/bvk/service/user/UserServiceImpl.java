package com.backend.bvk.service.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.bvk.dto.request.UserRequest;
import com.backend.bvk.model.user.User;
import com.backend.bvk.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findOrCreateUser(UserRequest payload) {
        Optional<User> user = this.userRepository.findByEmail(payload.getEmail()); 
        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(payload.getEmail());
            newUser.setName(payload.getName());
            this.userRepository.save(newUser);
            return newUser;
        } else {
            return user.get();
        }
    }
    
}
