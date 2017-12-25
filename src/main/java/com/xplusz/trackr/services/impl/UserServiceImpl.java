package com.xplusz.trackr.services.impl;

import com.xplusz.trackr.conf.ApplicationConfigurationProperties;
import com.xplusz.trackr.exceptions.EntityNotFoundException;
import com.xplusz.trackr.model.User;
import com.xplusz.trackr.repository.UserRepository;
import com.xplusz.trackr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends BaseService implements UserService{

    private final UserRepository userRepository;
    private final ApplicationConfigurationProperties configuration;

    @Autowired
    public UserServiceImpl(
            final UserRepository userRepository,
            final ApplicationConfigurationProperties configuration,
            final MongoOperations mongoOperations) {
        super(mongoOperations);
        this.userRepository = userRepository;
        this.configuration = configuration;
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User findUserByPrimaryKey(String id) {
        Optional<User> user = userRepository.findByPrimaryKey(id);
        return user.orElseThrow(() -> new EntityNotFoundException("User"));
    }

    @Override
    public Page<User> getUsersByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User save(User input) {
        input.setPassword(encryptPassword(input.getPassword()));
        input.setRoles(configuration.getDefaultUserRoles());
        return this.userRepository.save(input);
    }

    @Override
    public User update(String userId, User input) {
        if (!StringUtils.isEmpty(input.getPassword())) {
            input.setPassword(
                    encryptPassword(input.getPassword())
            );
        }
        return (User) super.update(userId, input);
    }

    @Override
    public void delete(String userId) {
        userRepository.delete(userId);
    }

    @Override
    public User findByUserName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.orElseThrow(() -> new EntityNotFoundException("User"));
    }

    public Page<User> findByCompany(String company, Pageable pageable) {
        return userRepository.findByCompany(company, pageable);
    }

    public List<User> findByCompany(String company) {
        return userRepository.findByCompany(company);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
