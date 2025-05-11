package org.mecipe.server.model.request.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class UserLoginDTO implements Serializable {

    private int loginTypeCode;

    private String username;

    private String password;

    private String phoneNumber;

    private String email;

}
