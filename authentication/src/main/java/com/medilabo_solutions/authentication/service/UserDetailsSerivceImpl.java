package com.medilabo_solutions.authentication.service;

import com.medilabo_solutions.authentication.exeption.PasswordNotFoundException;
import com.medilabo_solutions.authentication.model.User;
import com.medilabo_solutions.authentication.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsSerivceImpl implements UserDetailsService {

    @Autowired
    private UserRepository dbUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = dbUserRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Email" + "" + "does not match any user");
        }

        if (user.get().getPassword() == null || user.get().getPassword().isEmpty()) {
            throw new PasswordNotFoundException("Wrong password");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.get().getPassword())
                .build();
        return userDetails;
    }
}


