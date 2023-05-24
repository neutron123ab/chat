DROP DATABASE IF EXISTS `chat`;
CREATE DATABASE `chat`;
USE `chat`;

CREATE TABLE IF NOT EXISTS `user`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`    VARCHAR(128) NOT NULL COMMENT '用户名',
    `password`    VARCHAR(128) NOT NULL COMMENT '密码',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除（0-正常，1-已删除）',
    PRIMARY KEY (`id`),
    CONSTRAINT user_uk UNIQUE (`username`)
    ) COMMENT '用户表';

CREATE TABLE IF NOT EXISTS `group`
(
    `id`             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `group_owner_id` BIGINT       NOT NULL COMMENT '群主id',
    `group_name`     VARCHAR(128) NOT NULL COMMENT '群组名',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除（0-正常，1-已删除）',
    PRIMARY KEY (`id`),
    CONSTRAINT group_uk UNIQUE (`group_owner_id`)
    ) comment '群组表';

CREATE TABLE IF NOT EXISTS `user_group`
(
    `id`          BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     BIGINT   NOT NULL COMMENT '成员id',
    `group_id`    BIGINT   NOT NULL COMMENT '队伍id',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   TINYINT  NOT NULL DEFAULT 0 COMMENT '逻辑删除（0-正常，1-已删除）',
    PRIMARY KEY (`id`)
    ) COMMENT '用户-群组关联表';