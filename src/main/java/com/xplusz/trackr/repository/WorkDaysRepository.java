package com.xplusz.trackr.repository;

import com.xplusz.trackr.model.Claim;
import com.xplusz.trackr.model.Festival;
import com.xplusz.trackr.model.Workdays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface WorkDaysRepository extends MongoRepository<Workdays, String> {
    Optional<Workdays> findByPrimaryKey(final String primaryKey);

    Page<Workdays> findByCompany(String company, Pageable pageable);
}
