package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.BoardOfDirectorsDTO;
import com.runaitec.credimacpato.entity.BoardOfDirectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface BoardOfDirectorsMapper {
    BoardOfDirectorsDTO toDto(BoardOfDirectors entity);
    BoardOfDirectors toEntity(BoardOfDirectorsDTO dto);
}
