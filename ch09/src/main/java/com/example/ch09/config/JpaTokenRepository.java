package com.example.ch09.config;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaTokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findTokenByIdentifier(String identifier);
}
