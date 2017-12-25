package com.xplusz.trackr.repository;

import com.xplusz.trackr.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by hank on 4/10/17.
 */
@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
    Optional<Company> findByPrimaryKey(final String primaryKey);

    Company findByCompanyName(String name);
}
