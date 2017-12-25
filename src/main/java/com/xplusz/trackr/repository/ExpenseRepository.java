package com.xplusz.trackr.repository;


import com.xplusz.trackr.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    Optional<Expense> findByPrimaryKey(final String primaryKey);

    Page<Expense> findByUserId(String id, Pageable pageable);
}
