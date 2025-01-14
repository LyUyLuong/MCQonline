package com.javaweb.service.impl;

import com.javaweb.builder.TestSearchBuilder;
import com.javaweb.converter.TestConverter;
import com.javaweb.converter.TestSearchBuilderConverter;
import com.javaweb.entity.PartTestEntity;
import com.javaweb.entity.TestEntity;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.request.TestSearchRequest;
import com.javaweb.model.response.TestSearchResponse;
import com.javaweb.repository.PartTestRepository;
import com.javaweb.repository.TestRepository;
import com.javaweb.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TestService implements ITestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    TestConverter testConverter;

    @Autowired
    TestSearchBuilderConverter testSearchBuilderConverter;

    @Autowired
    PartTestRepository partTestRepository;

    @Override
    public List<TestDTO> getTests() {

        List<TestEntity> testEntities = testRepository.findAll();
        List<TestDTO> testDTOS = new ArrayList<>();
        for (TestEntity testEntity : testEntities) {
            TestDTO testDTO = testConverter.toDTO(testEntity);

            testDTOS.add(testDTO);
        }
        return testDTOS;
    }

    @Override
    public TestDTO getTestById(Long id) {
        return testRepository.findById(id)
                .map(testConverter::toDTO)
                .orElse(null);
    }

    @Override
    public List<TestDTO> haveanAlytics(){



        return null;
    }

    @Override
    public int countTotalItems(TestSearchRequest testSearchRequest) {
        TestSearchBuilder testSearchBuilder = testSearchBuilderConverter.toTestSearchBuilder(testSearchRequest);
        return testRepository.countTotalItem(testSearchBuilder);
    }

    @Override
    public List<TestSearchResponse> findAll(TestSearchRequest params, PageRequest pageable) {
        TestSearchBuilder testSearchBuilder = testSearchBuilderConverter.toTestSearchBuilder(params);
        List<TestEntity> testEntities = testRepository.findAll(testSearchBuilder, pageable);

        List<TestSearchResponse> testSearchResponseList = new ArrayList<>();
        for (TestEntity testEntity : testEntities) {
            TestSearchResponse testSearchResponse = testConverter.toSearchResponse(testEntity);

            testSearchResponseList.add(testSearchResponse);
        }
        return testSearchResponseList;
    }

    @Override
    @Transactional
    public TestEntity createOrUpdateTest(TestEntity testEntity) {
        // Nếu ID tồn tại, tìm `TestEntity` trong DB để cập nhật
        TestEntity existingTestEntity = null;
        if (testEntity.getId() != null) {
            existingTestEntity = testRepository.findById(testEntity.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đề thi với ID: " + testEntity.getId()));

            // Cập nhật các trường cơ bản
            existingTestEntity.setName(testEntity.getName());
            existingTestEntity.setDescription(testEntity.getDescription());
            existingTestEntity.setStatus(testEntity.getStatus());
            existingTestEntity.setNumberOfParticipants(testEntity.getNumberOfParticipants());

            // Xử lý các phần thi (PartTestEntities)
            List<PartTestEntity> existingParts = existingTestEntity.getPartTestEntities();

            // Xóa liên kết cũ
            for (PartTestEntity part : existingParts) {
                part.setTestEntity(null);
            }
            existingParts.clear();

            // Gán các phần mới
            if (testEntity.getPartTestEntities() != null) {
                for (PartTestEntity newPart : testEntity.getPartTestEntities()) {
                    PartTestEntity partTestEntity = partTestRepository.findById(newPart.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phần thi với ID: " + newPart.getId()));
                    partTestEntity.setTestEntity(existingTestEntity);
                    existingParts.add(partTestEntity);
                }
            }

            // Lưu thực thể đã cập nhật
            return testRepository.save(existingTestEntity);

        } else {
            // Nếu không có ID, tạo mới `TestEntity`
            if (testEntity.getPartTestEntities() != null) {
                for (PartTestEntity part : testEntity.getPartTestEntities()) {
                    PartTestEntity partTestEntity = partTestRepository.findById(part.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phần thi với ID: " + part.getId()));
                    partTestEntity.setTestEntity(testEntity);
                }
            }

            // Lưu thực thể mới
            return testRepository.save(testEntity);
        }
    }

}
