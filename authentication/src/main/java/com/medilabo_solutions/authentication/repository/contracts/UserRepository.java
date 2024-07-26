package com.medilabo_solutions.authentication.repository.contracts;

import com.medilabo_solutions.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
