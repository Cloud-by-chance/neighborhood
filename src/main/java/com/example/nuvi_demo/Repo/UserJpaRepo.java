package com.example.nuvi_demo.Repo;



import com.example.nuvi_demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    Optional<User> findBySUid(String email); //계정 구분짓기 위한 Uid를 위한 것, 이메일로 조회를 한다.

}