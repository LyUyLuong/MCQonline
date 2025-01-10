package com.javaweb.repository.custom.impl;

import com.javaweb.builder.TestSearchBuilder;
import com.javaweb.entity.TestEntity;
import com.javaweb.model.request.TestSearchRequest;
import com.javaweb.repository.custom.TestRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TestRepositoryCustomImpl implements TestRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private void joinSql(TestSearchBuilder testSearchBuilder, StringBuilder joinSql) {
        // Nếu có logic JOIN liên quan đến các bảng khác, thêm vào đây
        // Hiện tại TestEntity không có bảng liên quan cần JOIN
    }

    private void whereSql(TestSearchBuilder testSearchBuilder, StringBuilder whereSql) {
        // Điều kiện tìm kiếm theo tên
        if (testSearchBuilder.getName() != null && !testSearchBuilder.getName().isEmpty()) {
            whereSql.append("AND t.name LIKE '%").append(testSearchBuilder.getName()).append("%' ");
        }

        // Điều kiện tìm kiếm theo trạng thái
        if (testSearchBuilder.getStatus() != null) {
            whereSql.append("AND t.status = ").append(testSearchBuilder.getStatus()).append(" ");
        }
    }

    private void normalSql(TestSearchBuilder testSearchBuilder, StringBuilder whereSql) {
        try {
            Field[] fields = TestSearchBuilder.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (!fieldName.equals("name") && !fieldName.equals("status")) {
                    Object value = field.get(testSearchBuilder);
                    if (value != null) {
                        if (field.getType().getName().equals("java.lang.Long")
                                || field.getType().getName().equals("java.lang.Integer")) {
                            whereSql.append("AND t.").append(fieldName).append(" = ").append(value).append(" ");
                        } else {
                            whereSql.append("AND t.").append(fieldName).append(" LIKE '%").append(value).append("%' ");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TestEntity> findAll(TestSearchBuilder testSearchBuilder, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT t FROM TestEntity t ");
        StringBuilder joinSql = new StringBuilder("");
        StringBuilder whereSql = new StringBuilder("WHERE 1=1 ");

        // Xây dựng các phần của truy vấn
        joinSql(testSearchBuilder, joinSql);
        whereSql(testSearchBuilder, whereSql);
        normalSql(testSearchBuilder, whereSql);

        sql.append(joinSql).append(whereSql);

        // Tạo truy vấn với phân trang
        Query query = entityManager.createQuery(sql.toString(), TestEntity.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

    @Override
    public int countTotalItem(TestSearchBuilder testSearchBuilder) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(t.id) FROM TestEntity t ");
        StringBuilder joinSql = new StringBuilder("");
        StringBuilder whereSql = new StringBuilder("WHERE 1=1 ");

        // Xây dựng các phần của truy vấn
        joinSql(testSearchBuilder, joinSql);
        whereSql(testSearchBuilder, whereSql);
        normalSql(testSearchBuilder, whereSql);

        sql.append(joinSql).append(whereSql);

        Query query = entityManager.createQuery(sql.toString());

        return ((Number) query.getSingleResult()).intValue();
    }
}
