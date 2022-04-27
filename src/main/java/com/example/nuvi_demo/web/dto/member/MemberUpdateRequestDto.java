package com.example.nuvi_demo.web.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

@Getter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class MemberUpdateRequestDto {
    @Id
    private String user_id;
    private String nick_name;
//    private String passwd;
    private String email;
//    private int age;
//    private int region_id;

    @Builder
    public MemberUpdateRequestDto(String user_id, String nick_name, String email) {
        this.user_id = user_id;
        this.nick_name = nick_name;
//        this.passwd = passwd;
        this.email = email;
//        this.age = age;
//        this.region_id = region_id;
    }
}
