package com.nuvi.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String nickName;

    public UserDTO() {
    }

    public UserDTO(String id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }
}
