use mecipe_server;

-- 用户表
CREATE TABLE IF NOT EXISTS `t_user`
(
    id           BIGINT      NOT NULL AUTO_INCREMENT COMMENT '逻辑主键',
    username     VARCHAR(50) NOT NULL COMMENT '用户名，业务主键',
    password     CHAR(60)    NOT NULL COMMENT '加密后的密码',
    nick_name    VARCHAR(50) NOT NULL DEFAULT '' COMMENT '昵称',
    avatar_url   VARCHAR(255)         DEFAULT NULL COMMENT '头像链接',
    gender       TINYINT     NOT NULL DEFAULT 0 COMMENT '性别 0-未知 1-男 2-女',
    email        VARCHAR(80) NOT NULL COMMENT '邮箱',
    phone_number VARCHAR(20)          DEFAULT NULL COMMENT '手机号',
    status       TINYINT     NOT NULL DEFAULT 1 COMMENT '用户状态：0-禁用 1-正常',
    create_time  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted   TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_username` (`username`),
    KEY `idx_is_deleted` (`is_deleted`),
    KEY `idx_username_deleted` (`username`, `is_deleted`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户表，存储用户基本信息与审计信息';
