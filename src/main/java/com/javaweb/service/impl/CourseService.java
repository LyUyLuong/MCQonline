package com.javaweb.service.impl;

import com.javaweb.builder.CourseSearchBuilder;
import com.javaweb.builder.TestSearchBuilder;
import com.javaweb.converter.CourseConverter;
import com.javaweb.converter.CourseSearchBuilderConverter;
import com.javaweb.entity.CourseEntity;
import com.javaweb.entity.TestEntity;
import com.javaweb.model.dto.CourseDTO;
import com.javaweb.model.request.CourseRequest;
import com.javaweb.model.response.CourseReponse;
import com.javaweb.model.response.TestSearchResponse;
import com.javaweb.repository.CourseRepository;
import com.javaweb.service.ICourseService;
import com.javaweb.utils.UploadFileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CourseService implements ICourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseSearchBuilderConverter courseSearchBuilderConverter;

    @Autowired
    CourseConverter courseConverter;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UploadFileUtils uploadFileUtils;

    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public CourseEntity getCourseDetails(Long courseId) {
        return courseRepository.findById(courseId).get();
    }

    @Override
    public List<CourseReponse> findAll(CourseRequest params, PageRequest pageable) {
        CourseSearchBuilder courseSearchBuilder = courseSearchBuilderConverter.toSearchBuilder(params);
        List<CourseEntity> courseEntities = courseRepository.findAll(courseSearchBuilder, pageable);

        List<CourseReponse> courseReponseList = new ArrayList<>();
        for (CourseEntity courseEntity : courseEntities) {
            CourseReponse courseReponse = courseConverter.toReponse(courseEntity);

            courseReponseList.add(courseReponse);
        }
        return courseReponseList;
    }

    @Override
    public int countTotalItems(CourseRequest params) {
        return  courseRepository.countTotalItem(courseSearchBuilderConverter.toSearchBuilder(params));
    }

    @Override
    @Transactional
    public void createOrUpdateCourse(CourseDTO courseDTO) {
        // Kiểm tra và tạo thực thể mới từ DTO
        CourseEntity courseEntity = courseConverter.toEntity(courseDTO);

        if (courseDTO.getId() != null && courseRepository.existsById(courseDTO.getId())) {
            CourseEntity existingCourse = courseRepository.findById(courseDTO.getId()).orElseThrow(() -> new RuntimeException("Course not found"));
            courseEntity.setImage(existingCourse.getImage()); // Giữ hình ảnh cũ nếu không cập nhật
        }

        // Xử lý hình ảnh
        try {
            saveThumbnail(courseDTO, courseEntity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lưu hình ảnh", e);
        }

        // Lưu dữ liệu vào cơ sở dữ liệu
        courseRepository.save(courseEntity);
    }




    public void saveThumbnail(CourseDTO courseDTO, CourseEntity courseEntity) {
        String path = courseEntity.getImage();

        if (courseDTO.getImageBase64() != null && !courseDTO.getImageBase64().isEmpty()) {
            path = "/course/" + System.currentTimeMillis() + "_" + courseDTO.getImageName();

            if (courseEntity.getImage() != null && !path.equals(courseEntity.getImage())) {
                File oldFile = new File("C://home/images" + courseEntity.getImage());
                if (oldFile.exists() && oldFile.delete()) {
                    System.out.println("Old image file is deleted: " + oldFile.getPath());
                }
            }

            try {
                String base64Data = courseDTO.getImageBase64();
                if (base64Data.contains(",")) {
                    base64Data = base64Data.split(",")[1];
                }

                byte[] bytes = Base64.decodeBase64(base64Data.getBytes(StandardCharsets.UTF_8));
                uploadFileUtils.writeOrUpdate("C://home/images" + path, bytes);

                courseEntity.setImage(path);
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi xử lý hình ảnh", e);
            }
        }
    }






}