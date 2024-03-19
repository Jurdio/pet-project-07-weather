package edu.weather.domain.service;

import edu.weather.controller.dto.UserDTO;
import edu.weather.domain.model.User;
import edu.weather.domain.repository.UserRepository;
import edu.weather.mapper.UserMapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    public User createUser(UserDTO userDTO){
        return userRepository.save(userMapper.toUser(userDTO));
    }
    public Optional<User> getUserByLogin(String login){
        return userRepository.findByLogin(login);
    }
    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }


}
