package com.practice_jwt_2.service.iml;

import com.practice_jwt_2.model.User;
import com.practice_jwt_2.model.jwt.UserPrinciple;
import com.practice_jwt_2.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserPrinciple.build(userRepository.findByUsername(username));
    }
}
