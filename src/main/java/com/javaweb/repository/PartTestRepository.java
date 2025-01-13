package com.javaweb.repository;

import com.javaweb.entity.PartTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartTestRepository extends JpaRepository<PartTestEntity, Long> {
    List<PartTestEntity> findAllByTestEntityIsNull();

}
