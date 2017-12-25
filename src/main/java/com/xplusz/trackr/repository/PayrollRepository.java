package com.xplusz.trackr.repository;

import com.xplusz.trackr.model.Payroll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends MongoRepository<Payroll, String> {

    Optional<Payroll> findByPrimaryKey(final String primaryKey);

    Optional<Payroll> findByUserNameAndMonth(String username, String month);

    Page<Payroll> findByCompanyAndMonth(String company, String month, Pageable pageable);
}
