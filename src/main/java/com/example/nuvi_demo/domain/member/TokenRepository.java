package com.example.nuvi_demo.domain.member;

import com.example.nuvi_demo.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
}
