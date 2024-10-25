package com.yash.MovieRoger.repository;

import com.yash.MovieRoger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByMobile(String mobile);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
}

