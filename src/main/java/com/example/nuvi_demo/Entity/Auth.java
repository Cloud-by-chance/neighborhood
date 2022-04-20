package com.example.nuvi_demo.Entity;

//Auth 정보 즉 Token을 위한 Entity

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@ToString
@Builder // builder를 사용할수 있게 합니다.
@Entity  // jpa entity임을 알립니다.
@Getter  // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "token") // 'token' 테이블과 매핑됨을 명시
public class Auth {
    @Id
    private String Refresh_token;

    @Column
    private String Access_token;

    @Column(unique = true)
    private String user_id;

    public void update (String Access_token){
        this.Access_token = Access_token; //업데이트는 Access token만 업데이트 하면 된다.
    }

}
