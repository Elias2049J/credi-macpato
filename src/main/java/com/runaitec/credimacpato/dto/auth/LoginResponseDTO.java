package com.runaitec.credimacpato.dto.auth;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponseDTO {
    private UserResponseDTO user;

    private String sessionId;
}
