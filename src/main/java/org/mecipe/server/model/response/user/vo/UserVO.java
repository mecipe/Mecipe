package org.mecipe.server.model.response.user.vo;

import lombok.*;
import org.mecipe.server.model.response.LoginToken;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {

    private String username;

    private String nickName;

    private String avatarUrl;

    private Integer gender;

    private String email;

    private String phoneNumber;

    private LocalDateTime createTime;

    private LoginToken loginToken;

}
