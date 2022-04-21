package com.example.nuvi_demo.domain.token;

import com.example.nuvi_demo.domain.Entity.TokenVo;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@ToString
@Builder // builder를 사용할수 있게 합니다.
@Entity  // jpa entity임을 알립니다.
@Getter  // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "token")
@DynamicUpdate
@DynamicInsert
public class Token {
    @Id
    private String user_id;
    private String idx;
    private String Access_token;

    private String Refresh_token;

    @Column(updatable = false,insertable = false)
    private Timestamp timestamp;


    public Token(String user_id, TokenVo tokenVo) {
        this.user_id = user_id;
        this.Access_token = tokenVo.getJwt();
        this.Refresh_token = tokenVo.getRefreshToken();
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }

}

