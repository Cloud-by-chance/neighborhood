package com.example.nuvi_demo.web.dto.member;

import com.example.nuvi_demo.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class MemberSaveRequestDto {
    @Id
    private String user_id;
    @Version
    @Transient
    @Column(insertable = false, updatable = false)
    private Long version;
    private String nick_name;
    private String passwd;
    private String email;
    private int age;
    private int region_id;

    @Builder
    public MemberSaveRequestDto(String user_id, String nick_name, String passwd, String email, int age, int region_id) {
        this.user_id = user_id;
        this.nick_name = nick_name;
        this.passwd = passwd;
        this.email = email;
        this.age = age;
        this.region_id = region_id;
    }

    public Member toEntity() {
        return Member.builder().user_id(user_id)
                .nick_name(nick_name)
                .email(email)
                .age(age)
                .region_id(region_id)
                .build();
    }
}
