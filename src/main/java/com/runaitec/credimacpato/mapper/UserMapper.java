package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.UserDTO;
import com.runaitec.credimacpato.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DebtMapper.class})
public interface UserMapper {
    UserDTO toDto(User entity);
    User toEntity(UserDTO dto);
}
