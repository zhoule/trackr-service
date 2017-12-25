package com.xplusz.trackr.repository;

import com.xplusz.trackr.model.Claim;
import com.xplusz.trackr.model.HolidayBalances;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by hank on 3/28/17.
 */
public interface HolidayBalancesRepository extends MongoRepository<HolidayBalances, String> {
    Optional<HolidayBalances> findByPrimaryKey(final String primaryKey);

    Page<HolidayBalances> findByUsername(final String user, Pageable pageable);

    Page<HolidayBalances> findByCompany(String company, Pageable pageable);

}
