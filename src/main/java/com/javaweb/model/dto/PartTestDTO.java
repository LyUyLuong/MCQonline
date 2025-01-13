package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartTestDTO {
    private Long id;
    private String partName;

    public PartTestDTO() {
    }

    public PartTestDTO(Long id) {
        this.id = id;
    }
}
