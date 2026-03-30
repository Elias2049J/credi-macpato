package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.ReportDTO;
import com.runaitec.credimacpato.entity.Report;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportDTO toDto(Report entity);
    Report toEntity(ReportDTO dto);
}
