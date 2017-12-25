package com.xplusz.trackr.repository;

import com.xplusz.trackr.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{

    Optional<User> findByPrimaryKey(final String primaryKey);

    Optional<User> findByUserName(String userName);

    Page<User> findByCompany(String company, Pageable pageable);

    List<User> findByCompany(String company);
}
