package com.example.nuvi_demo.Repo;



import com.example.nuvi_demo.domain.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, String> {

//    Optional<User> findByUser_id(String user_id); //계정 구분짓기 위한 Uid를 위한 것, 이메일로 조회를 한다.


}