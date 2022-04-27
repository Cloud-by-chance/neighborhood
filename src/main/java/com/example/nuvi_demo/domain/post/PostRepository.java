//<<<<<<< HEAD
package com.example.nuvi_demo.domain.post;

import com.example.nuvi_demo.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p, m.nick_name FROM Post p JOIN Member m on p.user_id = m.user_id ORDER BY p.post_id DESC")
    List<Post> findAllDesc();
}
//=======
//package com.example.nuvi_demo.domain.post;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface PostRepository extends JpaRepository<Post, Long> {
//    @Query("SELECT p FROM Post p ORDER BY p.post_id DESC")
//    List<Post> findAllDesc();
//}
//>>>>>>> 650992229e08e4f7b83e8049b8f9c3badcc70b48
