package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.UserDTO;
import com.runaitec.credimacpato.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.List;

@Mapper(componentModel = "spring", uses = {LoanMapper.class})
public interface UserMapper {
    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "username", source = "username"),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "lastname", source = "lastname"),
        @Mapping(target = "password", source = "password"),
        @Mapping(target = "role", source = "role"),
        @Mapping(target = "active", source = "active"),
        @Mapping(target = "createdAt", source = "createdAt"),
        @Mapping(target = "loans", source = "loans")
    })
    UserDTO toDto(User entity);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "username", source = "username"),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "lastname", source = "lastname"),
        @Mapping(target = "password", source = "password"),
        @Mapping(target = "role", source = "role"),
        @Mapping(target = "active", source = "active"),
        @Mapping(target = "createdAt", source = "createdAt"),
        @Mapping(target = "loans", source = "loans")
    })
    User toEntity(UserDTO dto);
}
