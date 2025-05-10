-- 用户表
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 宠物表
CREATE TABLE `pet` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '宠物名称',
  `breed` int NOT NULL COMMENT '品种(0-猫,1-狗,2-其他)',
  `age` int DEFAULT NULL COMMENT '年龄',
  `gender` int NOT NULL COMMENT '性别(0-公,1-母)',
  `status` int NOT NULL COMMENT '状态(0-家养,1-流浪)',
  `image` varchar(255) DEFAULT NULL COMMENT '图片URL',
  `is_lost` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否丢失',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宠物表';

-- 领养信息表
CREATE TABLE `adoption` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pet_id` bigint NOT NULL COMMENT '宠物ID',
  `user_id` bigint NOT NULL COMMENT '发布者/领养人ID',
  `health` varchar(32) NOT NULL COMMENT '健康状况',
  `province` varchar(32) NOT NULL COMMENT '省份',
  `city` varchar(32) NOT NULL COMMENT '城市',
  `description` text COMMENT '详细描述',
  `image` varchar(1000) DEFAULT NULL COMMENT '图片URL',
  `requirements` text COMMENT '领养要求',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态(0-家养, 1-发布领养,2-待领养,3-领养成功,4-领养失败)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_pet_id` (`pet_id`),
  KEY `idx_publisher_id` (`publisher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='领养信息表'; 