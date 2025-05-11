package org.mecipe.server.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class UserEntity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("nick_name")
    private String nickName;

    @TableField("avatar_url")
    private String avatarUrl;

    @TableField("gender")
    private Integer gender;

    @TableField("email")
    private String email;

    @TableField("phone_number")
    private String phoneNumber;

    @TableField("status")
    private Integer status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

}
