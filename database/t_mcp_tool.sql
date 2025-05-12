-- MCP实体表
CREATE TABLE IF NOT EXISTS `t_mcp_tool`
(
    id            INT          NOT NULL AUTO_INCREMENT COMMENT '主键ID, 自增',
    mcp_id        INT          NOT NULL COMMENT 'MCP实体ID，引用 t_mcp_entity.id',
    tool_name VARCHAR(100) NOT NULL COMMENT 'tool名',
    description   TEXT                  DEFAULT NULL COMMENT '描述信息',
    params        JSON                  DEFAULT NULL COMMENT '参数',
    create_time   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted    TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    INDEX `idx_mcp_id` (`mcp_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT = 'MCP Tool，用于存储MCP提供的Tool的基本信息';