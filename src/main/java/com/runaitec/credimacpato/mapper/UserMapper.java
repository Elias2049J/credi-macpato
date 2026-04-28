package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.user.*;
import com.runaitec.credimacpato.dto.user.association.AssociationRequestDTO;
import com.runaitec.credimacpato.dto.user.association.AssociationResponseDTO;
import com.runaitec.credimacpato.dto.user.customer.BusinessCustomerRequestDTO;
import com.runaitec.credimacpato.dto.user.customer.BusinessCustomerResponseDTO;
import com.runaitec.credimacpato.dto.user.customer.PersonCustomerRequestDTO;
import com.runaitec.credimacpato.dto.user.customer.PersonCustomerResponseDTO;
import com.runaitec.credimacpato.dto.user.vendor.*;
import com.runaitec.credimacpato.entity.user.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper{

    @Mapping(source = "registrationName", target = "registrationName")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "registrationName", target = "fullName")
    AssociationResponseDTO toResponseDto(Association entity);

    @Mapping(source = "association.id", target = "associationId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "moneyBalance", target = "moneyBalance")
    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    PersonVendorResponseDTO toResponseDto(PersonVendor entity);

    @Mapping(source = "association.id", target = "associationId")
    @Mapping(source = "registrationName", target = "registrationName")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "moneyBalance", target = "moneyBalance")
    @Mapping(target = "fullName", expression = "java(entity.getRegistrationName())")
    BusinessVendorResponseDTO toResponseDto(BusinessVendor entity);

    @Mapping(source = "association.id", target = "associationId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    PersonCustomerResponseDTO toResponseDto(PersonCustomer entity);

    @Mapping(source = "association.id", target = "associationId")
    @Mapping(source = "registrationName", target = "registrationName")
    @Mapping(source = "address", target = "address")
    @Mapping(target = "fullName", expression = "java(entity.getRegistrationName())")
    BusinessCustomerResponseDTO toResponseDto(BusinessCustomer entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "vendors", ignore = true)
    @Mapping(target = "customers", ignore = true)
    Association toEntity(AssociationRequestDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    PersonVendor toEntity(PersonVendorRequestDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    BusinessVendor toEntity(BusinessVendorRequestDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    PersonCustomer toEntity(PersonCustomerRequestDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    BusinessCustomer toEntity(BusinessCustomerRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAssociationFromDto(AssociationRequestDTO dto, @MappingTarget Association entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePersonVendorFromDto(PersonVendorRequestDTO dto, @MappingTarget PersonVendor entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBusinessVendorFromDto(BusinessVendorRequestDTO dto, @MappingTarget BusinessVendor entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePersonCustomerFromDto(PersonCustomerRequestDTO dto, @MappingTarget PersonCustomer entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBusinessCustomerFromDto(BusinessCustomerRequestDTO dto, @MappingTarget BusinessCustomer entity);

    default UserResponseDTO toResponseDtoDispatch(User entity) {
        return switch (entity) {
            case Association association -> toResponseDto(association);
            case PersonVendor pp -> toResponseDto(pp);
            case BusinessVendor bp -> toResponseDto(bp);
            case PersonCustomer pc -> toResponseDto(pc);
            case BusinessCustomer bc -> toResponseDto(bc);
            default -> throw new IllegalArgumentException("User type not supported: " + entity.getClass().getSimpleName());
        };
    }

    default User toEntityDispatch(UserRequestDTO dto) {
        return switch (dto) {
            case AssociationRequestDTO a -> toEntity(a);
            case PersonVendorRequestDTO pp -> toEntity(pp);
            case BusinessVendorRequestDTO bp -> toEntity(bp);
            case PersonCustomerRequestDTO pc -> toEntity(pc);
            case BusinessCustomerRequestDTO bc -> toEntity(bc);
            default -> throw new IllegalArgumentException("UserRequestDTO type not supported: " + dto.getClass().getSimpleName());
        };
    }

    default void updateFromDtoDispatch(UserRequestDTO dto, @MappingTarget User entity) {

        switch (dto) {
            case AssociationRequestDTO a when entity instanceof Association e ->
                    updateAssociationFromDto(a, e);
            case PersonVendorRequestDTO pv when entity instanceof PersonVendor e ->
                    updatePersonVendorFromDto(pv, e);
            case BusinessVendorRequestDTO bv when entity instanceof BusinessVendor e ->
                    updateBusinessVendorFromDto(bv, e);
            case PersonCustomerRequestDTO pc when entity instanceof PersonCustomer e ->
                    updatePersonCustomerFromDto(pc, e);
            case BusinessCustomerRequestDTO bc when entity instanceof BusinessCustomer e ->
                    updateBusinessCustomerFromDto(bc, e);
            default -> throw new IllegalArgumentException(
                    "Mismatch between DTO and entity: " + dto.getClass().getSimpleName()
            );
        }
    }

    default Long mapPartnerToId(Vendor vendor) {
        return vendor == null ? null : vendor.getId();
    }
}
