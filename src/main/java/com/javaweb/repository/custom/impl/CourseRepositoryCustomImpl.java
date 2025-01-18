package com.javaweb.repository.custom.impl;

import com.javaweb.builder.CourseSearchBuilder;
import com.javaweb.entity.CourseEntity;
import com.javaweb.repository.custom.CourseRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.List;

@Repository
public class CourseRepositoryCustomImpl implements CourseRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private void joinSql(CourseSearchBuilder courseSearchBuilder, StringBuilder joinSql) {
        // If there are JOINs needed for related tables, handle them here
        // Currently, CourseEntity doesn't require JOINs
    }

    private void whereSql(CourseSearchBuilder courseSearchBuilder, StringBuilder whereSql) {
        // Search condition for name
        if (courseSearchBuilder.getName() != null && !courseSearchBuilder.getName().isEmpty()) {
            whereSql.append("AND c.name LIKE '%").append(courseSearchBuilder.getName()).append("%' ");
        }

        // Search condition for status
        if (courseSearchBuilder.getStatus() != null && !courseSearchBuilder.getStatus().isEmpty()) {
            whereSql.append("AND c.status = '").append(courseSearchBuilder.getStatus()).append("' ");
        }
    }

    private void normalSql(CourseSearchBuilder courseSearchBuilder, StringBuilder whereSql) {
        try {
            Field[] fields = CourseSearchBuilder.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (!fieldName.equals("name") && !fieldName.equals("status")) {
                    Object value = field.get(courseSearchBuilder);
                    if (value != null) {
                        if (field.getType().getName().equals("java.lang.Long") ||
                                field.getType().getName().equals("java.lang.Integer")) {
                            whereSql.append("AND c.").append(fieldName).append(" = ").append(value).append(" ");
                        } else {
                            whereSql.append("AND c.").append(fieldName).append(" LIKE '%").append(value).append("%' ");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CourseEntity> findAll(CourseSearchBuilder courseSearchBuilder, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT c FROM CourseEntity c ");
        StringBuilder joinSql = new StringBuilder("");
        StringBuilder whereSql = new StringBuilder("WHERE 1=1 ");

        // Build query parts
        joinSql(courseSearchBuilder, joinSql);
        whereSql(courseSearchBuilder, whereSql);
        normalSql(courseSearchBuilder, whereSql);

        sql.append(joinSql).append(whereSql);

        // Create query with pagination
        Query query = entityManager.createQuery(sql.toString(), CourseEntity.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

    @Override
    public int countTotalItem(CourseSearchBuilder courseSearchBuilder) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(c.id) FROM CourseEntity c ");
        StringBuilder joinSql = new StringBuilder("");
        StringBuilder whereSql = new StringBuilder("WHERE 1=1 ");

        // Build query parts
        joinSql(courseSearchBuilder, joinSql);
        whereSql(courseSearchBuilder, whereSql);
        normalSql(courseSearchBuilder, whereSql);

        sql.append(joinSql).append(whereSql);

        Query query = entityManager.createQuery(sql.toString());

        return ((Number) query.getSingleResult()).intValue();
    }
}
