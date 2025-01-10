package com.javaweb.api.web;

import com.javaweb.model.response.QuestionDetailResponse;
import com.javaweb.service.impl.QuestionService;
import com.javaweb.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionAPI {

    @Autowired
    private QuestionService questionService;

    @Autowired
    UserService userService;

    @GetMapping("/{questionId}/result/{resultId}")
    public ResponseEntity<QuestionDetailResponse> getQuestionDetail(@PathVariable Long questionId,
                                                                    @PathVariable Long resultId) {

        QuestionDetailResponse questionDetail = questionService.findDetailByQuestionIdAndResultId(questionId,resultId);
        return ResponseEntity.ok(questionDetail);
    }
}
