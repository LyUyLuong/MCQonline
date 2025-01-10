package com.javaweb.repository;

import com.javaweb.entity.TestEntity;
import com.javaweb.repository.custom.TestRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long>, TestRepositoryCustom {
}
