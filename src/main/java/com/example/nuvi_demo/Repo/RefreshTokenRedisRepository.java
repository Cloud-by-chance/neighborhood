package com.example.nuvi_demo.Repo;

import java.util.Optional;
import com.example.nuvi_demo.domain.Entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findById(String id);
}
