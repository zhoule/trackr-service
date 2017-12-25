package com.xplusz.trackr.repository;


import com.xplusz.trackr.model.Claim;
import com.xplusz.trackr.model.Festival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FestivalRepository extends MongoRepository<Festival, String> {

    Optional<Festival> findByPrimaryKey(final String primaryKey);

    Page<Festival> findByCompany(String company, Pageable pageable);

    List<Festival> findByCommon(boolean common);
}
