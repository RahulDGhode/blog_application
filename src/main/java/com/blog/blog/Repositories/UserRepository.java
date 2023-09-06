package com.blog.blog.Repositories;

import com.blog.blog.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String Email);
    Optional<User> findByUsernameOrEmail (String Email, String Username);
    Optional<User> findByUsername(String Username);
    Boolean existsByEmail(String Email);
    Boolean existsByUsername(String Username);
}
