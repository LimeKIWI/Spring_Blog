package com.blog.hw3.repository;

import com.blog.hw3.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepositroy extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByKey(String key);
}
