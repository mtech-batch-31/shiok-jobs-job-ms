package com.mtech.sjmsjob.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.mtech.sjmsjob.entity.Job;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface JobRepository extends PagingAndSortingRepository<Job, Long> {

    Optional<Job> findById(Long id);

     @Query(value = "SELECT * FROM search_job(:keywords_input, :employmentTypes_input, :workLocations_input, :minimumSalary_input, :skills_input)"
            , countQuery = "SELECT count(*) FROM search_job(:keywords_input, :employmentTypes_input, :workLocations_input, :minimumSalary_input, :skills_input)"
            ,nativeQuery = true)
    Page<Job> search(@Param("keywords_input") String keywords,
                     @Param("employmentTypes_input") String employmentType,
                     @Param("workLocations_input") String workLocations,
                     @Param("minimumSalary_input") BigDecimal minimumSalary,
                     @Param("skills_input") String skills, Pageable pageable);

}
