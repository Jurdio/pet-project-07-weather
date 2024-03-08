package edu.weather.domain.service;

import edu.weather.domain.model.dto.UserDTO;
import edu.weather.domain.repository.UserRepository;
import edu.weather.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.thymeleaf.engine.IElementDefinitionsAware;

public class RegistrationService {
    private final UserService userService = new UserService();
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

}
