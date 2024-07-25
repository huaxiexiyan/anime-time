-- 创建 app授权类型实体 表
DROP TABLE IF EXISTS anime;
create table anime
(
    id                 bigint not null comment '动漫id',
    season_id          bigint not null comment '唯一id',
    title              varchar(100) comment '标题，原名',
    sub_title          varchar(100) comment '副标题',
    cover              varchar(1000) comment '标题，原名',
    horizontal_picture varchar(1000) comment '标题，原名',
    link               varchar(1000) comment '链接地址',
    release_time       datetime comment '发行时间',
    summary            text comment '简介',

    created_by         bigint comment '创建人',
    created_time       datetime default now() comment '创建时间',
    last_modified_by   bigint comment '最后修改人',
    last_modified_time datetime comment '最后修改时间',
    deleted_by         bigint comment '删除人',
    deleted_time       datetime comment '删除时间',
    is_deleted         int4     default 0 comment '是否已逻辑删除，0是没有，1是已删除',

    primary key (id)
) engine = InnoDB comment ='动漫信息表';

# 动漫筛选项
DROP TABLE IF EXISTS anime_condition;
create table anime_condition
(
    id                 bigint not null comment '筛选id',
    parent_id          bigint not null default 0 comment '上级组名',
    name               varchar(100) comment '筛选名称',
    field              varchar(100) comment '查询字段名',
    keyword            varchar(1000) comment '查询值',
    sort               int4   not null default 0 comment '排序字段，值越大排序越靠后',

    created_by         bigint comment '创建人',
    created_time       datetime        default now() comment '创建时间',
    last_modified_by   bigint comment '最后修改人',
    last_modified_time datetime comment '最后修改时间',
    deleted_by         bigint comment '删除人',
    deleted_time       datetime comment '删除时间',
    is_deleted         int4            default 0 comment '是否已逻辑删除，0是没有，1是已删除',

    primary key (id)
) engine = InnoDB comment ='动漫筛选项表';
