package com.javaweb.api.web;

import com.javaweb.entity.ResultEntity;
import com.javaweb.entity.UserAnswerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.dto.UserDTO;
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
    UserService userService;

    @Autowired
    ResultService resultService;

    @Autowired
    TestService testService;

    @PostMapping("/{testid}")
    public ResponseEntity<String> submitSheet(@PathVariable Long testid, @RequestBody List<UserAnswerRaw> userAnswerList) {

        if (testid == null || testid <= 0 || userAnswerList == null || userAnswerList.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid data provided.");
        }

        UserDTO user = userService.findOneByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        TestDTO test = testService.getTestById(testid);

        if (user == null || test == null) {
            return ResponseEntity.notFound().build();
        }
        ResultEntity resultEntity = resultService.sumbitSheet(user, test, userAnswerList);

        return ResponseEntity.ok("Test submitted successfully.");
    }
}

