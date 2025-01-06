package com.javaweb.api.web;

import com.javaweb.entity.ResultEntity;
import com.javaweb.entity.UserAnswerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.enums.TestType;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.model.raw.FormRaw;
import com.javaweb.model.raw.UserAnswerRaw;
import com.javaweb.service.impl.ResultService;
import com.javaweb.service.impl.TestService;
import com.javaweb.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/result")
public class ResultAPI {



    @Autowired
    ResultService resultService;

    @Autowired
    TestService testService;


    @PostMapping("/{testid}")
    public ResponseEntity<String> submitSheet(@PathVariable Long testid,
                                              @RequestBody FormRaw form,
                                              @RequestParam(value = "part", required = false) List<String> parts) {

        if (testid == null || testid <= 0 || form.getUserAnswerRawList() == null || form.getUserAnswerRawList().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid data provided.");
        }

        TestDTO test = testService.getTestById(testid);

        String testType = TestType.Full_Test.name();

        if (parts != null && !parts.isEmpty()) {
            testType = TestType.Parts_Test.name();
        }

        ResultEntity resultEntity = resultService.sumbitSheet(test, form,testType);

        String resultLink = String.format("/test/%d/result/%d", testid, resultEntity.getId());

        return ResponseEntity.ok(resultLink);
    }
}

