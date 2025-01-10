package com.javaweb.model.response;

import com.javaweb.model.dto.AbstractDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestSearchResponse extends AbstractDTO {

    private Long id;
    private String name;
    private String description;
    private Long numberOfParticipants;
    private Integer status;

}
