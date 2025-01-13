package com.javaweb.repository;

import com.javaweb.entity.ResultHavePartsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultHavePartsRepository extends JpaRepository<ResultHavePartsEntity, Long> {
}
