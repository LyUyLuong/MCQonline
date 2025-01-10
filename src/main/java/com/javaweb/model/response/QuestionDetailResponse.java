package com.javaweb.model.response;

import com.javaweb.entity.AnswerEntity;
import com.javaweb.entity.QuestionTestEntity;
import com.javaweb.model.dto.AnswerDTO;
import com.javaweb.model.dto.QuestionTestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDetailResponse {
    private QuestionTestDTO questionTestDTO;
    private List<AnswerDTO> answers;
    private AnswerDTO userAnswer;

    public QuestionDetailResponse(QuestionTestDTO questionTestDTO, List<AnswerDTO> answers, AnswerDTO userAnswer) {
        this.questionTestDTO = questionTestDTO;
        this.answers = answers;
        this.userAnswer = userAnswer;
    }
}
