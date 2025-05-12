use mecipe_server;

-- MCP实体表
CREATE TABLE IF NOT EXISTS `t_mcp`
(
    id          INT          NOT NULL AUTO_INCREMENT COMMENT '主键ID, 自增',
    name        VARCHAR(100) NOT NULL COMMENT '名称',
    description TEXT                  DEFAULT NULL COMMENT '描述信息',
    introduce   TEXT                  DEFAULT NULL COMMENT '介绍信息',
    icon        VARCHAR(255)          DEFAULT NULL COMMENT '图标URL',
    type        VARCHAR(50)  NOT NULL COMMENT '类型标识',
    author_id   BIGINT COMMENT '用户ID，引用 t_user.id',
    author_name VARCHAR(100) NOT NULL DEFAULT 'official' COMMENT '作者ID',
    mcp_version VARCHAR(20)  NOT NULL DEFAULT '1.0.0' COMMENT 'MCP版本号',
    source      TINYINT(1)   NOT NULL DEFAULT 0 COMMENT 'MCP服务来源：0-local 1-remote',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '授权状态：1-启用 0-禁用',
    create_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_name` (`name`),
    KEY `idx_type` (`type`),
    KEY `idx_author_id` (`author_id`),
    KEY `idx_author_name` (`author_name`),
    KEY `idx_is_deleted` (`is_deleted`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT = 'MCP实体表，用于存储MCP模块的基本信息';