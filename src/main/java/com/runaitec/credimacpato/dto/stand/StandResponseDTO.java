package com.runaitec.credimacpato.dto.stand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StandResponseDTO {
    private Long id;

    private Integer number;

    private Long ownerId;
}
