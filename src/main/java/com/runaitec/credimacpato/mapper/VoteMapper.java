package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.VoteDTO;
import com.runaitec.credimacpato.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "loanRequestId", source = "loanRequest.id"),
        @Mapping(target = "userId", source = "user.id"),
        @Mapping(target = "approved", source = "approved")
    })
    VoteDTO toDto(Vote entity);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "loanRequest", ignore = true),
        @Mapping(target = "user", ignore = true),
        @Mapping(target = "approved", source = "approved")
    })
    Vote toEntity(VoteDTO dto);
}
