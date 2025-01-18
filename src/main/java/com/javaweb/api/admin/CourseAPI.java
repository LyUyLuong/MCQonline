package com.javaweb.api.admin;


import com.javaweb.entity.CourseEntity;
import com.javaweb.entity.PartTestEntity;
import com.javaweb.entity.TestEntity;
import com.javaweb.model.dto.CourseDTO;
import com.javaweb.model.dto.PartTestDTO;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.service.impl.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/courses")
public class CourseAPI {

    @Autowired
    CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> getCourseDetails(@PathVariable Long id) {
        CourseEntity course = courseService.getCourseDetails(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createOrUpdateTest(@RequestBody CourseDTO courseDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessages);
            }



            // Gọi service xử lý lưu trữ
            courseService.createOrUpdateCourse(courseDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body("Đề thi đã được tạo hoặc cập nhật thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi xử lý yêu cầu: " + e.getMessage());
        }
    }
}
