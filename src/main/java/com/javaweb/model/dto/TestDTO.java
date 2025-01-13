package com.javaweb.model.dto;

import com.javaweb.entity.PartTestEntity;
import com.javaweb.entity.ResultEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TestDTO extends AbstractDTO {

    private Long id;

    private String name;

    private String description;

    private List<PartTestDTO> partTestEntities;

    private List<ResultEntity> resultEntities;

    private Long numberOfParticipants;

    private Integer status;

}
