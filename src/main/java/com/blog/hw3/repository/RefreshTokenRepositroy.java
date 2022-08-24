package com.blog.hw3.repository;

import com.blog.hw3.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepositroy extends JpaRepository<RefreshToken, String> {
}
