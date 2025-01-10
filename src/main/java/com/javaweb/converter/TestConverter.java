package com.javaweb.converter;

import com.javaweb.entity.TestEntity;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.response.TestSearchResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestConverter {

    @Autowired
    private ModelMapper modelMapper;

    public TestDTO toDTO(TestEntity item) {
            TestDTO dto = modelMapper.map(item, TestDTO.class);
            return dto;
    }

    public TestEntity toEntity(TestDTO item) {
        TestEntity entity = modelMapper.map(item, TestEntity.class);
        return entity;
    }

    public TestSearchResponse toSearchResponse(TestEntity testEntity) {
        TestSearchResponse entity = modelMapper.map(testEntity, TestSearchResponse.class);
        return entity;
    }

}
