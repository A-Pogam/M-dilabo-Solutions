package com.medilabo_solutions.authentication.service;

import com.medilabo_solutions.authentication.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserDetailsSerivceImpl implements UserDetailsService {

    @Autowired
    private UserRepository dbUserRepository;


}
