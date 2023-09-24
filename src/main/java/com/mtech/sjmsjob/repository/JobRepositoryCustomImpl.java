package com.mtech.sjmsjob.repository;

import com.mtech.sjmsjob.entity.Job;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

public class JobRepositoryCustomImpl implements JobRespositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;
    public Page<Job> search(String[] jobTitles, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> query = cb.createQuery(Job.class);
        Root<Job> job = query.from(Job.class);

        Path<String> jobTitlePath = job.get("jobTitle");
        Predicate[] predicates = new Predicate[jobTitles.length];
        for(int i=0;i<jobTitles.length;i++) {
            predicates[i] = cb.like(jobTitlePath, jobTitles[i]);
        }

        int start = (int) pageable.getOffset();

        query.select(job)
                .where(cb.or(predicates))
                .orderBy(QueryUtils.toOrders(pageable.getSort(),job,cb));

        var result = entityManager.createQuery(query).setFirstResult(start)
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(result, pageable, getTotalCount(cb, predicates));
    }

    private Long getTotalCount(CriteriaBuilder criteriaBuilder, Predicate[] predicateArray) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Job> root = criteriaQuery.from(Job.class);

        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(predicateArray);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
