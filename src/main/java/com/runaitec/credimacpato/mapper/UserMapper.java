package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.user.*;
import com.runaitec.credimacpato.dto.user.customer.BusinessCustomerRequestDTO;
import com.runaitec.credimacpato.dto.user.customer.BusinessCustomerResponseDTO;
import com.runaitec.credimacpato.dto.user.customer.PersonCustomerRequestDTO;
import com.runaitec.credimacpato.dto.user.customer.PersonCustomerResponseDTO;
import com.runaitec.credimacpato.dto.user.partner.*;
import com.runaitec.credimacpato.entity.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "partners", target = "partnerIds")
    @Mapping(source = "registrationName", target = "registrationName")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "moneyBalance", target = "moneyBalance")
    @Mapping(target = "fullName", expression = "java(entity.getRegistrationName())")
    AssociationResponseDTO toResponseDto(Association entity);

    @Mapping(source = "association.id", target = "associationId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "moneyBalance", target = "moneyBalance")
    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    PersonPartnerResponseDTO toResponseDto(PersonPartner entity);

    @Mapping(source = "registrationName", target = "registrationName")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "moneyBalance", target = "moneyBalance")
    @Mapping(target = "fullName", expression = "java(entity.getRegistrationName())")
    BusinessPartnerResponseDTO toResponseDto(BusinessPartner entity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    PersonCustomerResponseDTO toResponseDto(PersonCustomer entity);

    @Mapping(source = "registrationName", target = "registrationName")
    @Mapping(source = "address", target = "address")
    @Mapping(target = "fullName", expression = "java(entity.getRegistrationName())")
    BusinessCustomerResponseDTO toResponseDto(BusinessCustomer entity);

    @Mapping(target = "partners", ignore = true)
    @Mapping(source = "moneyBalance", target = "moneyBalance")
    Association toEntity(AssociationRequestDTO dto);

    @Mapping(source = "associationId", target = "association.id")
    PersonPartner toEntity(PersonPartnerRequestDTO dto);

    BusinessPartner toEntity(BusinessPartnerRequestDTO dto);

    PersonCustomer toEntity(PersonCustomerRequestDTO dto);

    BusinessCustomer toEntity(BusinessCustomerRequestDTO dto);

    default UserResponseDTO toResponseDtoDispatch(User entity) {
        return switch (entity) {
            case Association association -> toResponseDto(association);
            case PersonPartner pp -> toResponseDto(pp);
            case BusinessPartner bp -> toResponseDto(bp);
            case PersonCustomer pc -> toResponseDto(pc);
            case BusinessCustomer bc -> toResponseDto(bc);
            default -> throw new IllegalArgumentException("User type not supported: " + entity.getClass().getSimpleName());
        };
    }

    default User toEntityDispatch(UserRequestDTO dto) {
        return switch (dto) {
            case AssociationRequestDTO a -> toEntity(a);
            case PersonPartnerRequestDTO pp -> toEntity(pp);
            case BusinessPartnerRequestDTO bp -> toEntity(bp);
            case PersonCustomerRequestDTO pc -> toEntity(pc);
            case BusinessCustomerRequestDTO bc -> toEntity(bc);
            default -> throw new IllegalArgumentException("UserRequestDTO type not supported: " + dto.getClass().getSimpleName());
        };
    }

    default Long mapPartnerToId(Partner partner) {
        return partner == null ? null : partner.getId();
    }
}
