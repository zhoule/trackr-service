package com.xplusz.trackr.security;

import com.xplusz.trackr.model.User;
import com.xplusz.trackr.repository.UserRepository;
import com.xplusz.trackr.security.UserDetailsDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<User> userOptional = userRepository.findByUserName(username);

        if (userOptional.isPresent()) {
            return new UserDetailsDecorator(userOptional.get());
        }

        throw new UsernameNotFoundException(username);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
