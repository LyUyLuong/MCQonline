package com.javaweb.model.dto;

import com.javaweb.entity.PartTestEntity;
import com.javaweb.entity.ResultEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultHavePartsDTO {

    private ResultEntity result;

    private PartTestEntity partTest;

}
