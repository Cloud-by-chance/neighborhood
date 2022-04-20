package com.example.nuvi_demo.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//UserVo는 로그인 할때 id/password를 받기위한 객체!

@Data // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
public class UserVo {

    @JsonProperty
    private String id;

    @JsonProperty
    private String password;

//    @JsonProperty
//    private String nickName;
}
