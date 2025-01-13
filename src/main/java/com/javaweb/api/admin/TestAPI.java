package com.javaweb.api.admin;


import com.javaweb.entity.PartTestEntity;
import com.javaweb.entity.TestEntity;
import com.javaweb.model.dto.PartTestDTO;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.repository.PartTestRepository;
import com.javaweb.service.impl.PartTestService;
import com.javaweb.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/tests")
public class TestAPI {

    @Autowired
    TestService testService;
    @Autowired
    private PartTestService partTestService;
    @Autowired
    private PartTestRepository partTestRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createOrUpdateTest(@RequestBody TestDTO testDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessages);
            }

            // Map PartTestDTO sang PartTestEntity nếu cần
            List<PartTestEntity> partTests = new ArrayList<>();
            for(PartTestDTO partTestDTO : testDTO.getPartTestEntities()){
                PartTestEntity partTestEntity = partTestRepository.findById(partTestDTO.getId()).get();
                partTests.add(partTestEntity);
            }


            TestEntity testEntity = new TestEntity();
            testEntity.setName(testDTO.getName());
            testEntity.setDescription(testDTO.getDescription());
            testEntity.setPartTestEntities(partTests);

            // Gọi service xử lý lưu trữ
            testService.createOrUpdateTest(testEntity);

            return ResponseEntity.status(HttpStatus.CREATED).body("Đề thi đã được tạo hoặc cập nhật thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi xử lý yêu cầu: " + e.getMessage());
        }
    }



}
