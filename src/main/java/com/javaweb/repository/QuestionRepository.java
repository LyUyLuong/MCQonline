package com.javaweb.repository;

import com.javaweb.entity.QuestionTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionTestEntity,Long> {
}
