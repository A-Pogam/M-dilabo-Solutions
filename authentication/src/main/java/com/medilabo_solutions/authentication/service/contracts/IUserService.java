package com.medilabo_solutions.authentication.service.contracts;

import com.medilabo_solutions.authentication.model.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface IUserService {
    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    void updateUser(User user);

    void registerNewUser(User user);

    boolean existsByEmail(String email);

    boolean registerUser(User user);

    void save(User user);
}
