package edu.weather.domain.service;

import edu.weather.domain.model.dto.UserDTO;
import edu.weather.domain.model.entity.User;
import edu.weather.domain.repository.UserRepository;
import edu.weather.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public User createUser(UserDTO userDTO){
        return userRepository.save(userMapper.toUser(userDTO));
    }


}
