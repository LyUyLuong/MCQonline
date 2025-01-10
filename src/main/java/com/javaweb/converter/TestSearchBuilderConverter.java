package com.javaweb.converter;

import com.javaweb.builder.TestSearchBuilder;
import com.javaweb.model.request.TestSearchRequest;
import com.javaweb.utils.MapUtils;
import org.springframework.stereotype.Component;

@Component
public class TestSearchBuilderConverter {

    public TestSearchBuilder toTestSearchBuilder(TestSearchRequest testSearchRequest) {

        return new TestSearchBuilder.Builder()
                .setName(MapUtils.getObject(testSearchRequest.getName(),String.class))
                .setStatus(MapUtils.getObject(testSearchRequest.getStatus(),Integer.class))
                .build();
    }

}
