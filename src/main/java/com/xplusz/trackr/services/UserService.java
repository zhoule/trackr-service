package com.xplusz.trackr.services;


import com.xplusz.trackr.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User findUserByPrimaryKey(String id);

    User findByUserName(String username);

    Page<User> getUsersByPage(Pageable pageable);

    User save(User input);

    User update(final String userId, User input);

    void delete(final String userId);

}
