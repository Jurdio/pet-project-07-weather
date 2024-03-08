package edu.weather.mapper;

import edu.weather.domain.model.dto.UserDTO;
import edu.weather.domain.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mindrot.jbcrypt.BCrypt;

@Mapper
public interface UserMapper {

    UserDTO toUserDTO(User user);


//    @Mapping(source = "password", target = "password", qualifiedByName = "hashPassword")
    User toUser(UserDTO userDTO);

    @Named("hashPassword")
    static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
