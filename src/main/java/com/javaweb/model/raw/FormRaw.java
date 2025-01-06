package com.javaweb.model.raw;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;


@Getter
@Setter
public class FormRaw {
    List<UserAnswerRaw> userAnswerRawList = new LinkedList<UserAnswerRaw>();
    String completeTime;
}
