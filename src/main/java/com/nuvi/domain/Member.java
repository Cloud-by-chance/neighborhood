package com.nuvi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "USER")
@DynamicInsert
@DynamicUpdate
public class Member {
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
    @Temporal(TemporalType.DATE)
    private Date create_dt;
    @Temporal(TemporalType.DATE)
    private Date update_dt;
    private int is_del;
    private int region_id;

    @Builder
    public Member(String user_id, String nick_name, String passwd, String email, int age, int region_id) {
        this.user_id = user_id;
        this.nick_name = nick_name;
        this.passwd = passwd;
        this.email = email;
        this.age = age;
        this.region_id = region_id;
    }

    @Override
    public String toString() {
        return "nick_name : " + getNick_name() + "\n";
    }
}
