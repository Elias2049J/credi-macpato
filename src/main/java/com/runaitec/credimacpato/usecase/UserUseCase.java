package com.runaitec.credimacpato.usecase;

import com.runaitec.credimacpato.dto.UserDTO;
import com.runaitec.credimacpato.dto.UserLeavingSummaryDTO;

public interface UserUseCase {
    UserLeavingSummaryDTO leaveOrganization(Long userId);
    UserDTO login(UserDTO userDTO);
    UserDTO register(UserDTO userDto);
}