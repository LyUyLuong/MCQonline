package com.javaweb.service;

import com.javaweb.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface IAnswerService {
    AnswerEntity findById(Long id);
}
