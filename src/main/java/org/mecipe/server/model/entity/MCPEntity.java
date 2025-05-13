package org.mecipe.server.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_mcp")
public class MCPEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @TableField("introduce")
    private String introduce;

    @TableField("icon")
    private String icon;

    @TableField("type")
    private String type;

    @TableField("author_id")
    private Integer authorId;

    @TableField("author_name")
    private String authorName;

    @TableField("mcp_version")
    private String mcpVersion;

    // MCP服务来源：0-local 1-remote
    @TableField("source")
    private int source;

    // 1: 启用, 0: 禁用
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
