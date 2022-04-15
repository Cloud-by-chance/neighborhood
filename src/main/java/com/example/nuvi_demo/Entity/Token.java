package com.example.nuvi_demo.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@ToString
@Builder // builder를 사용할수 있게 합니다.
@Entity  // jpa entity임을 알립니다.
@Getter  // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "token")
public class Token {

    @Id
    private String idx;

    private String jwt;
    private String user_id;
    private String refresh_tk;
    private LocalDateTime create_dt;
    private LocalDateTime update_dt;

    @Builder
    public Token(String jwt, String user_id, String refresh_tk) {
        this.jwt = jwt;
        this.user_id = user_id;
        this.refresh_tk = refresh_tk;
    }
}

