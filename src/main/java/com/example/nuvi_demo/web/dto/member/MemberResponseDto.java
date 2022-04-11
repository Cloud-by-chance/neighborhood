package com.example.nuvi_demo.web.dto.member;

import com.example.nuvi_demo.domain.member.Member;
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
public class MemberResponseDto {
    @Id
    private String user_id;
    private String nick_name;
    private String passwd;
    private String email;
    private int age;
    private int region_id;

    @Builder
    public MemberResponseDto(Member entity) {
        this.user_id = entity.getUser_id();
        this.nick_name = entity.getNick_name();
        this.passwd = entity.getPasswd();
        this.email = entity.getEmail();
        this.age = entity.getAge();
        this.region_id = entity.getRegion_id();
    }
}
