package com.xplusz.trackr.repository;

import com.xplusz.trackr.model.Festival;
import com.xplusz.trackr.model.HolidayPolicy;
import com.xplusz.trackr.model.HolidayType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HolidayPolicyRepository extends MongoRepository<HolidayPolicy, String> {
    Optional<HolidayPolicy> findByPrimaryKey(final String primaryKey);

    Page<HolidayPolicy> findByCompany(String company, Pageable pageable);
}
