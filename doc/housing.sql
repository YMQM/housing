use housing;

/*
  攻略相关
*/
create table `community` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `unique_key` varchar(64) COMMENT '小区唯一标识',
    `title` varchar(128) NOT NULL COMMENT '小区名称',
    `province_code` varchar(20) NOT NULL COMMENT '省份code',
    `province_name` varchar(10) NOT NULL COMMENT '省份（一级行政区）名称',
    `city_code` varchar(20) NOT NULL COMMENT '市级行政区code',
    `city_name` varchar(15) NOT NULL COMMENT '市级行政区名称',
    `county_code` varchar(20) NOT NULL COMMENT '县级行政区code',
    `county_name` varchar(20) NOT NULL COMMENT '县级行政区名称',
    `detail_address` varchar(128) NOT NULL COMMENT '小区详细地址',
    `creator_id` int NOT NULL COMMENt '创建人id',
    primary key(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '小区信息表';

create table `tag`(
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `title` varchar(20) NOT NULL COMMENT '标签名称',
    `project` varchar(15) NOT NULL COMMENT '所属项目',
    `type` varchar(15) NOT NULL COMMENT '标签分类',
    primary key(`id`),
    index index_tag_type(`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '标签表';

create table `house_picking_tip_basic`(
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `unique_key` varchar(64) COMMENT '攻略唯一标识',
    `city_name` varchar(15) NOT NULL COMMENT '攻略所属城市',
    `community_id` int NOT NULL COMMENT '小区id',
    `title` varchar(20) NOT NULL COMMENT '攻略标题',
    `survey_time` date NOT NULL COMMENT '踩盘时间',
    `creator_id` int NOT NULL COMMENT '创建者id',
    `creator_name` varchar(15) NOT NULL COMMENT '创建者姓名',
    `create_timestamp` bigint NOT NULL COMMENT '创建时间戳',
    `update_user_id` int COMMENT '最后一次更新操作者id',
    `update_timestamp` bigint COMMENT '最后一次更新操作时间',
    `reviewer_id` int COMMENT '审核人id',
    `review_timestamp` bigint COMMENT '审核时间',
    `state` tinyint COMMENT '攻略状态',
    `is_del` bit NOT NULL COMMENT '逻辑删除',
    primary key(`id`),
    index index_house_picking_tip_state(`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '购房攻略基础信息表';

create table `house_picking_tip_tag`(
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `house_tip_id` int NOT NULL COMMENT '攻略id',
    `tag_id` int NOT NULL COMMENT '标签id',
    primary key(`id`),
    index index_house_picking_tip_tag_id(`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '攻略标签关联表';

/*
  权限相关
*/
DROP TABLE IF EXISTS `admin_account`;
create table `admin_account`(
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `user_name` varchar(32) NOT NULL COMMENT '姓名',
    `email` varchar(24) NOT NULL COMMENT '邮箱',
    `phone_number` char(11) NOT NULL COMMENT '手机号',
    `password` char(128) NOT NULL COMMENT '密码',
    `salt` char(128) NOT NULL COMMENT '密码加盐',
    `remark` varchar(128) NOT NULL COMMENT '备注信息',
    `state` tinyint NOT NULL COMMENT '账号状态',
    `create_timestamp` bigint NOT NULL COMMENT '创建时间戳',
    `last_login` bigint COMMENT '最后登录时间',
    `is_del` bit NOT NULL COMMENT '逻辑删除',
    primary key(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '后台系统用户表';

create table `role` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `unique_key` varchar(32) NOT NULL COMMENT '唯一键',
    `role_name` varchar(32) NOT NULL COMMENT '角色名称',
    `is_active` bit NOT NULL COMMENT '角色是否启用',
    `desc` varchar(64) COMMENT '角色功能描述',
    `create_time` bigint NOT NULL COMMENT '创建时间戳',
    primary key(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '角色表';

DROP TABLE IF EXISTS `permission`;
create table `permission`
(
    `id`            int          NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `name`          varchar(128) NOT NULL COMMENT '权限名称',
    `unique_key`    varchar(128) NOT NULL COMMENT '权限唯一标识',
    `detail`        varchar(32) COMMENT '权限描述',
    `url`           varchar(128) COMMENT '匹配的请求地址',
    `is_need_check` bit          NOT NULL COMMENT '是否需要和用户持有权限做匹配',
    `parent_id`     int COMMENT '父级id',
    `is_leaf`       bit          NOT NULL COMMENT '是否为叶子权限节点',
    `tree_path`     varchar(32)  NOT NULL COMMENT '树结构路径',
    `create_time`   bigint       NOT NULL COMMENT '创建时间戳',
    primary key (`id`),
    unique key unique_key_permission_url (`url`),
    unique key unique_key_permission_uk (`unique_key`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '权限明细表';

create table `role_permission`(
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `role_id` int NOT NULL COMMENT '角色id',
    `permission_id` int NOT NULL COMMENT '权限id',
    primary key(`id`),
    index index_role_permission_role_id (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '角色权限分配关联表';

create table `account_role` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `account_id` int NOT NULL COMMENT '账号id',
    `role_id` int NOT NULL COMMENT '角色id',
    primary key(`id`),
    index index_acocunt_role_account_id(`account_id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户角色分配关联表';

/*
  系统管理相关表
*/
create table `common_log`(
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `operator_id` int NOT NULL COMMENT '操作者id',
    `operator_name` varchar(32) NOT NULL COMMENT '操作者姓名',
    `type` tinyint NOT NULL COMMENT '操作类型',
    `title` varchar(32) NOT NULL COMMENT '操作名称',
    `action` json NOT NULL COMMENT '操作内容',
    `create_time` bigint NOT NULL COMMENT '操作记录时间戳',
    `ip` int NOT NULL COMMENT 'IP地址',
    `browser_name` varchar(20) COMMENT '浏览器名称',
    primary key(`id`),
    index index_operate_log_type(`type`),
    index index_operate_log_type_operator_id(`type`, `operator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '日志记录表';
