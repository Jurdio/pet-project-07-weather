package edu.weather.domain.service;

import edu.weather.domain.model.dto.UserDTO;
import edu.weather.domain.repository.UserRepository;
import edu.weather.mapper.UserMapper;
import org.mapstruct.factory.Mappers;

public class RegistrationService {
    private final UserRepository userRepository = new UserRepository();
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    public void registerUser(UserDTO userDTO){
        userRepository.save(userMapper.toUser(userDTO));
    }
}
