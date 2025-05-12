package org.mecipe.server.model.request.user;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO implements Serializable {

    private String username;

    private String nickName;

    private String avatarUrl;

    private Integer gender;

    private String email;

    private String phoneNumber;

}
