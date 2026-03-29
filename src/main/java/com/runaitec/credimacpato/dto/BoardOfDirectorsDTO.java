package com.runaitec.credimacpato.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardOfDirectorsDTO {
    private Long id;
    @NotNull
    @NotEmpty
    private List<UserDTO> members;
}
