package edu.weather.domain.service;

import edu.weather.domain.model.entity.User;
import edu.weather.domain.repository.UserRepository;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    public void createUser(){
        userRepository.save(User.builder()
                .login("dima1233")
                .password("dima")
                .build());
    }
    public Optional<User> getUser(){
        return userRepository.findById(1);
    }


}
