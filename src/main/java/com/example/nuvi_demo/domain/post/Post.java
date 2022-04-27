//<<<<<<< HEAD
package com.example.nuvi_demo.domain.post;

import com.example.nuvi_demo.domain.board.Board;
import com.example.nuvi_demo.domain.file.File;
import com.example.nuvi_demo.domain.member.Member;
import com.example.nuvi_demo.domain.recommend.Recommend;
import com.example.nuvi_demo.domain.reply.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;
    private Long board_id;
    private String post_name;
    private String user_id;
    private int hits;
    private int reply_cnt;
    private int is_lock;
    private int reply_role;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int is_del;
    private LocalDateTime create_dt;
    private LocalDateTime update_dt;
    @Column(columnDefinition = "TEXT")
    private String content;
    // N : 1 관계
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name = "user_id", insertable=false, updatable=false),
//            @JoinColumn(name = "nick_name", insertable = false, updatable = false)
//    })
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", insertable=false, updatable=false)
    private Board board;
    // 1 : N 관계
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Recommend> recommendList;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Reply> replyList;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<File> fileList;

    @Builder
    public Post(String post_name, Long board_id, String user_id, int hits, int reply_cnt, int is_lock, int reply_role, String content) {
        this.post_name = post_name;
        this.board_id = board_id;
        this.user_id = user_id;
        this.hits = hits;
        this.reply_cnt = reply_cnt;
        this.is_lock = is_lock;
        this.reply_role = reply_role;
        this.content = content;
    }

    public void update(String post_name, String content) {
        this.post_name = post_name;
        this.content = content;
    }

    @Override
    public String toString() {
        return "post name : " + post_name + "\n" +
                "user_id : " + user_id + "\n" +
                "content : " + content + "\n";
    }
}
//=======
//package com.example.nuvi_demo.domain.post;
//
//import com.example.nuvi_demo.domain.board.Board;
//import com.example.nuvi_demo.domain.file.File;
//import com.example.nuvi_demo.domain.member.Member;
//import com.example.nuvi_demo.domain.reply.Reply;
//import com.example.nuvi_demo.domain.recommend.Recommend;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Getter
//@NoArgsConstructor
//@Entity
//@DynamicInsert
//@DynamicUpdate
//@Table(name = "post")
//public class Post {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long post_id;
//    private Long board_id;
//    private String post_name;
//    private String user_id;
//    private int hits;
//    private int reply_cnt;
//    private int is_lock;
//    private int reply_role;
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int is_del;
//    private LocalDateTime create_dt;
//    private LocalDateTime update_dt;
//    @Column(columnDefinition = "TEXT")
//    private String content;
//    // N : 1 관계
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", insertable=false, updatable=false)
//    private Member member;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id", insertable=false, updatable=false)
//    private Board board;
//    // 1 : N 관계
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "idx")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Recommend> recommendList;
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "idx")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Reply> replyList;
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<File> fileList;
//
//
//    @Builder
//    public Post(String post_name, Long board_id, String user_id, int hits, int reply_cnt, int is_lock, int reply_role, String content) {
//        this.post_name = post_name;
//        this.board_id = board_id;
//        this.user_id = user_id;
//        this.hits = hits;
//        this.reply_cnt = reply_cnt;
//        this.is_lock = is_lock;
//        this.reply_role = reply_role;
//        this.content = content;
//    }
//
//    public void update(String post_name, String content) {
//        this.post_name = post_name;
//        this.content = content;
//    }
//
//    @Override
//    public String toString() {
//        return "post name : " + post_name + "\n" +
//                "user_id : " + user_id + "\n" +
//                "content : " + content + "\n";
//    }
//}
//>>>>>>> 650992229e08e4f7b83e8049b8f9c3badcc70b48
