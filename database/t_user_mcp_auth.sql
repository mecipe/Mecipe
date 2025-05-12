-- 用户-MCP 权限表
CREATE TABLE IF NOT EXISTS `t_user_mcp_auth`
(
    id          BIGINT     NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id     BIGINT     NOT NULL COMMENT '用户ID，引用 t_user.id',
    mcp_id      INT        NOT NULL COMMENT 'MCP实体ID，引用 t_mcp_entity.id',
    auth_type   TINYINT    NOT NULL DEFAULT 1 COMMENT '授权类型：1-使用 2-编辑',
    expired_at  TIMESTAMP           DEFAULT NULL COMMENT '授权过期时间，NULL表示永不过期',
    create_time TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_mcp_id` (`mcp_id`),
    KEY `idx_status` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT = 'User-MCP授权关系表，用于控制用户对MCP模块的访问权限';