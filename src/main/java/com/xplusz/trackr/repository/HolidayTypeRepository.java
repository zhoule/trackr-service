package com.xplusz.trackr.repository;

import com.xplusz.trackr.model.Claim;
import com.xplusz.trackr.model.Festival;
import com.xplusz.trackr.model.HolidayType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HolidayTypeRepository extends MongoRepository<HolidayType, String> {
    Optional<HolidayType> findByPrimaryKey(final String primaryKey);

    Page<HolidayType> findByCompany(String company, Pageable pageable);
}
