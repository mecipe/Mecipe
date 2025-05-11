package org.mecipe.server.model.request.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class UserRegisterDTO implements Serializable {

    private String username;

    private String password;

    private String nickName;

    private String avatarUrl;

    private Integer gender;

    private String email;

    private String phoneNumber;

}
