package org.mecipe.server.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_mcp_auth")
public class UserMCPAuthEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Long userId;

    @TableField("mcp_id")
    private Integer mcpId;

    // 授权类型：1-使用 2-编辑
    @TableField("auth_type")
    private Integer authType;

    // 过期时间, -1 永久有效
    @TableField("expired_at")
    private LocalDateTime expiredAt;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

}