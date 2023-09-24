package com.mtech.sjmsjob.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.mtech.sjmsjob.entity.Job;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobRepository extends PagingAndSortingRepository<Job, Long> {

    Optional<Job> findById(Long id);

    //    @Query(value = "Select j FROM #{#entityName} j WHERE j.jobTitle IN :jobTitles")
//    Page<Job> search(@Param("jobTitles") Collection<String> jobTitles, Pageable pageable);

    // JPA does not support dynamic sorting for native queries
    @Query(value = "SELECT * FROM job j, to_tsvector(j.job_title || ' ' || j.job_summary) as searched, " +
            "to_tsquery('english', :searchTerms) query, " +
            "ts_rank(to_tsvector(j.job_title || ' ' || j.job_summary), query) as search_rank " +
            "WHERE search_rank > 0 " +
            "ORDER BY search_rank DESC", nativeQuery = true)
    Page<Job> search(@Param("searchTerms") String searchTerm,Pageable pageable );
}
