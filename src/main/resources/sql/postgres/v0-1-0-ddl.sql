-- 创建 app授权类型实体 表
DROP TABLE IF EXISTS anime;
create table anime
(
    id                 int8         not null,
    hash_id            varchar(100) not null,
    season_id          int4         not null,
    title              varchar(100),
    subtitle           varchar(100),
    cover              varchar(1000),
    horizontal_picture varchar(1000),
    link               varchar(1000),
    type               int2,
    status             int2,
    evaluate           text,
    pub_time           timestamp,
    is_started         int2,
    is_finish          int2,
    weekday            int2,

    created_by         int8,
    created_time       timestamp default now(),
    last_modified_by   int8,
    last_modified_time timestamp,
    deleted_by         int8,
    deleted_time       timestamp,
    is_deleted         int4      default 0,
    PRIMARY KEY ("id")
);
COMMENT ON TABLE anime IS '动漫详情表';
COMMENT ON COLUMN "anime"."hash_id" IS '唯一id';
COMMENT ON COLUMN "anime"."season_id" IS '平台唯一id';
COMMENT ON COLUMN "anime"."title" IS '标题';
COMMENT ON COLUMN "anime"."subtitle" IS '副标题';
COMMENT ON COLUMN "anime"."cover" IS '封面图片url';
COMMENT ON COLUMN "anime"."horizontal_picture" IS '横板封面图片url';
COMMENT ON COLUMN "anime"."link" IS '链接地址';
COMMENT ON COLUMN "anime"."type" IS '剧集类型，1：番剧 2：电影 3：纪录片4：国创 5：电视剧 7：综艺';
COMMENT ON COLUMN "anime"."status" IS '状态';
COMMENT ON COLUMN "anime"."evaluate" IS '评价、简介';
COMMENT ON COLUMN "anime"."pub_time" IS '发行时间';
COMMENT ON COLUMN "anime"."is_started" IS '是否已经开始放送';
COMMENT ON COLUMN "anime"."is_finish" IS '是否已经放送完成';
COMMENT ON COLUMN "anime"."weekday" IS '周几开始放送';

COMMENT ON COLUMN "anime"."created_by" IS '创建人';
COMMENT ON COLUMN "anime"."created_time" IS '创建时间';
COMMENT ON COLUMN "anime"."last_modified_by" IS '最后修改人';
COMMENT ON COLUMN "anime"."last_modified_time" IS '最后修改时间';
COMMENT ON COLUMN "anime"."deleted_by" IS '删除人';
COMMENT ON COLUMN "anime"."deleted_time" IS '删除时间';
COMMENT ON COLUMN "anime"."is_deleted" IS '是否已逻辑删除，0是没有，1是已删除';


-- 动漫筛选项
DROP TABLE IF EXISTS anime_condition;
create table anime_condition
(
    id                 int8 not null,
    parent_id          int8 not null default 0,
    name               varchar(100),
    field              varchar(100),
    keyword            varchar(1000),
    sort               int4 not null default 0,

    created_by         int8,
    created_time       timestamp     default now(),
    last_modified_by   int8,
    last_modified_time timestamp,
    deleted_by         int8,
    deleted_time       timestamp,
    is_deleted         int4          default 0,

    primary key (id)
);
COMMENT ON TABLE anime_condition IS '动漫筛选项表';
COMMENT ON COLUMN "anime_condition"."parent_id" IS '上级组名';
COMMENT ON COLUMN "anime_condition"."name" IS '筛选名称';
COMMENT ON COLUMN "anime_condition"."field" IS '查询字段名';
COMMENT ON COLUMN "anime_condition"."keyword" IS '查询值';
COMMENT ON COLUMN "anime_condition"."sort" IS '排序字段，值越大排序越靠后';

COMMENT ON COLUMN "anime_condition"."created_by" IS '创建人';
COMMENT ON COLUMN "anime_condition"."created_time" IS '创建时间';
COMMENT ON COLUMN "anime_condition"."last_modified_by" IS '最后修改人';
COMMENT ON COLUMN "anime_condition"."last_modified_time" IS '最后修改时间';
COMMENT ON COLUMN "anime_condition"."deleted_by" IS '删除人';
COMMENT ON COLUMN "anime_condition"."deleted_time" IS '删除时间';
COMMENT ON COLUMN "anime_condition"."is_deleted" IS '是否已逻辑删除，0是没有，1是已删除';
