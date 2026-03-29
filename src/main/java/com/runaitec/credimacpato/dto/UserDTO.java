package com.runaitec.credimacpato.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserDTO {
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String lastname;
    @NotNull
    private String password;
    @NotNull
    private String role;
    @NotNull
    private Boolean active;
    @NotNull
    private LocalDateTime createdAt;
}
