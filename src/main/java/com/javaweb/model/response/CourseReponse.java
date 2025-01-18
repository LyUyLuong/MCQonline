package com.javaweb.model.response;

import com.javaweb.model.dto.AbstractDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseReponse extends AbstractDTO {

    private String name;
    private Double price;
    private String category;
    private String status;


}
