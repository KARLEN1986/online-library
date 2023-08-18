package com.example.onlinelibrary.web.mappers;

import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.web.dto.user.UserDto;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting User and UserDto objects.
 * Uses the MapStruct library with Spring component model.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Converts a User object to a UserDto object.
     *
     * @param user The User object to be converted.
     * @return The corresponding UserDto object.
     */
    UserDto toDto(User user);

    /**
     * Converts a UserDto object to a User object.
     *
     * @param dto The UserDto object to be converted.
     * @return The corresponding User object.
     */
    User toEntity(UserDto dto);

}
