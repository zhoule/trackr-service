package com.xplusz.trackr.repository;

import com.xplusz.trackr.model.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClaimRepository extends MongoRepository<Claim, String> {

    Optional<Claim> findByPrimaryKey(final String primaryKey);

    Page<Claim> findByUserId(final String user, Pageable pageable);

    Page<Claim> findByCompany(String company, Pageable pageable);
}
