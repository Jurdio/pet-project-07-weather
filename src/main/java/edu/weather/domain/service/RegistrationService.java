package edu.weather.domain.service;

import edu.weather.mapper.UserMapper;
import org.mapstruct.factory.Mappers;

public class RegistrationService {
    private final UserService userService = new UserService();
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

}
