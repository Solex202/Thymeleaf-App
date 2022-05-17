package com.semicolon.Demo.Thymeleaf.Application.repository;

import com.semicolon.Demo.Thymeleaf.Application.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
