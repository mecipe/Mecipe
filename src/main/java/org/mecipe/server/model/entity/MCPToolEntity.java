package org.mecipe.server.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_mcp_tool")
public class MCPToolEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("mcp_id")
    private Integer mcpId;

    @TableField("tool_name")
    private String toolName;

    @TableField("description")
    private String description;

    // JSON 格式
    @TableField("params")
    private String params;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

}