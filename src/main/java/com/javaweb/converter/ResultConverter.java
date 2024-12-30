package com.javaweb.converter;


import com.javaweb.entity.ResultEntity;
import com.javaweb.model.dto.ResultDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultConverter {

    @Autowired
    private ModelMapper modelMapper;

    public ResultEntity toEntity(ResultDTO resultDTo) {

        ResultEntity resultEntity = modelMapper.map(resultDTo, ResultEntity.class);
        return resultEntity;

    }

}
