package com.runaitec.credimacpato.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class UserDTO {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private String role;
    private Boolean active;
    private LocalDateTime createdAt;
}
