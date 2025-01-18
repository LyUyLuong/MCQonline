package com.javaweb.converter;

import com.javaweb.builder.CourseSearchBuilder;
import com.javaweb.model.request.CourseRequest;
import com.javaweb.utils.MapUtils;
import org.springframework.stereotype.Component;

@Component
public class CourseSearchBuilderConverter {

    public CourseSearchBuilder toSearchBuilder (CourseRequest courseRequest) {
        return new CourseSearchBuilder.Builder()
                .setName(MapUtils.getObject(courseRequest.getName(),String.class))
                .setCategory(MapUtils.getObject(courseRequest.getCategory(),String.class))
                .setStatus(MapUtils.getObject(courseRequest.getStatus(),String.class))
                .setPrice(MapUtils.getObject(courseRequest.getPrice(),Long.class))
                .build();

    }

}
