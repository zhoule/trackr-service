package com.xplusz.trackr.repository;

import com.xplusz.trackr.model.Claim;
import com.xplusz.trackr.model.Festival;
import com.xplusz.trackr.model.HolidayRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by hank on 3/28/17.
 */
public interface HolidayRecordRepository extends MongoRepository<HolidayRecord, String> {
    Optional<HolidayRecord> findByPrimaryKey(final String primaryKey);

    Page<HolidayRecord> findByUsernameAndCompany(final String user, final String company, Pageable pageable);

    List<HolidayRecord> findByCompanyAndUsernameAndYear(final String company, final String user, final String year);

    Page<HolidayRecord> findByCompany(String company, Pageable pageable);
}
