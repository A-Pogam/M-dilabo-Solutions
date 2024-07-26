package com.medilabo_solutions.authentication.service;

import com.medilabo_solutions.authentication.model.User;
import com.medilabo_solutions.authentication.repository.contracts.UserRepository;
import com.medilabo_solutions.authentication.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findbyEmail(email);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById((long) id);

    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail (String email) {
        return findByEmail(email).isPresent();
    }

    public boolean registerUser(User user) {
        if (existsByEmail(user.getEmail())){
            return false;
        }
        registerNewUser(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

}
