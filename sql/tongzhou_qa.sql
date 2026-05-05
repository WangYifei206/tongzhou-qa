-- 同舟问答扩展 SQL
-- 执行顺序：
-- 1. 先执行 RuoYi 基础脚本 sql/ry_20260417.sql
-- 2. 再执行本脚本

-- ----------------------------
-- 1. 门户学生角色
-- ----------------------------
insert into sys_role values ('100', '校园用户', 'student', 100, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, '同舟问答前台用户角色');

-- ----------------------------
-- 2. 用户扩展档案
-- ----------------------------
drop table if exists tz_user_profile;
create table tz_user_profile (
  profile_id      bigint(20) not null auto_increment comment '档案ID',
  user_id         bigint(20) not null comment '用户ID',
  college         varchar(64) default '' comment '学院',
  grade           varchar(32) default '' comment '年级',
  major           varchar(64) default '' comment '专业',
  introduction    varchar(500) default '' comment '个人简介',
  status          char(1) default '0' comment '状态（0正常 1停用）',
  del_flag        char(1) default '0' comment '删除标志（0存在 2删除）',
  create_by       varchar(64) default '' comment '创建者',
  create_time     datetime comment '创建时间',
  update_by       varchar(64) default '' comment '更新者',
  update_time     datetime comment '更新时间',
  remark          varchar(500) default '' comment '备注',
  primary key (profile_id),
  unique key uk_tz_user_profile_user (user_id)
) engine=innodb comment='同舟问答用户扩展档案表';

-- ----------------------------
-- 3. 话题分类
-- ----------------------------
drop table if exists tz_category;
create table tz_category (
  category_id     bigint(20) not null auto_increment comment '分类ID',
  category_name   varchar(50) not null comment '分类名称',
  category_code   varchar(50) not null comment '分类编码',
  sort_num        int(11) default 1 comment '排序值',
  status          char(1) default '0' comment '状态（0正常 1停用）',
  description     varchar(255) default '' comment '分类说明',
  del_flag        char(1) default '0' comment '删除标志（0存在 2删除）',
  create_by       varchar(64) default '' comment '创建者',
  create_time     datetime comment '创建时间',
  update_by       varchar(64) default '' comment '更新者',
  update_time     datetime comment '更新时间',
  remark          varchar(500) default '' comment '备注',
  primary key (category_id),
  unique key uk_tz_category_code (category_code)
) engine=innodb comment='同舟问答分类表';

insert into tz_category values (1, '考研', 'kaoyan', 1, '0', '考研经验、择校与复习规划', '0', 'admin', sysdate(), '', null, '');
insert into tz_category values (2, '实习', 'internship', 2, '0', '实习投递、面试与转正建议', '0', 'admin', sysdate(), '', null, '');
insert into tz_category values (3, '选课', 'course', 3, '0', '课程推荐、避坑与学习建议', '0', 'admin', sysdate(), '', null, '');
insert into tz_category values (4, '竞赛', 'competition', 4, '0', '比赛选择、组队与备赛经验', '0', 'admin', sysdate(), '', null, '');
insert into tz_category values (5, '就业', 'career', 5, '0', '秋招春招、简历与求职路径', '0', 'admin', sysdate(), '', null, '');
insert into tz_category values (6, '校园生活', 'campus', 6, '0', '日常学习、社团、宿舍与校园信息', '0', 'admin', sysdate(), '', null, '');

-- ----------------------------
-- 4. 问题主表
-- ----------------------------
drop table if exists tz_question;
create table tz_question (
  question_id      bigint(20) not null auto_increment comment '问题ID',
  category_id      bigint(20) not null comment '分类ID',
  user_id          bigint(20) not null comment '发布用户ID',
  title            varchar(120) not null comment '问题标题',
  content          text comment '问题内容',
  status           char(1) default '0' comment '状态（0待审核 1已通过 2已驳回 3已下架）',
  reject_reason    varchar(255) default '' comment '驳回原因',
  is_anonymous     char(1) default 'Y' comment '是否匿名（Y是 N否）',
  view_count       int(11) default 0 comment '浏览次数',
  answer_count     int(11) default 0 comment '回答数',
  favorite_count   int(11) default 0 comment '收藏数',
  del_flag         char(1) default '0' comment '删除标志（0存在 2删除）',
  create_by        varchar(64) default '' comment '创建者',
  create_time      datetime comment '创建时间',
  update_by        varchar(64) default '' comment '更新者',
  update_time      datetime comment '更新时间',
  remark           varchar(500) default '' comment '备注',
  primary key (question_id),
  key idx_tz_question_user (user_id),
  key idx_tz_question_category (category_id),
  key idx_tz_question_status (status)
) engine=innodb comment='同舟问答问题表';

-- ----------------------------
-- 5. 回答表
-- ----------------------------
drop table if exists tz_answer;
create table tz_answer (
  answer_id         bigint(20) not null auto_increment comment '回答ID',
  question_id       bigint(20) not null comment '问题ID',
  user_id           bigint(20) not null comment '回答用户ID',
  content           text comment '回答内容',
  status            char(1) default '0' comment '状态（0待审核 1已通过 2已驳回 3已下架）',
  reject_reason     varchar(255) default '' comment '驳回原因',
  is_anonymous      char(1) default 'Y' comment '是否匿名（Y是 N否）',
  like_count        int(11) default 0 comment '点赞数',
  del_flag          char(1) default '0' comment '删除标志（0存在 2删除）',
  create_by         varchar(64) default '' comment '创建者',
  create_time       datetime comment '创建时间',
  update_by         varchar(64) default '' comment '更新者',
  update_time       datetime comment '更新时间',
  remark            varchar(500) default '' comment '备注',
  primary key (answer_id),
  key idx_tz_answer_question (question_id),
  key idx_tz_answer_user (user_id),
  key idx_tz_answer_status (status)
) engine=innodb comment='同舟问答回答表';

-- ----------------------------
-- 6. 收藏表
-- ----------------------------
drop table if exists tz_question_favorite;
create table tz_question_favorite (
  favorite_id       bigint(20) not null auto_increment comment '收藏ID',
  question_id       bigint(20) not null comment '问题ID',
  user_id           bigint(20) not null comment '用户ID',
  create_time       datetime comment '创建时间',
  primary key (favorite_id),
  unique key uk_tz_question_favorite (question_id, user_id)
) engine=innodb comment='同舟问答问题收藏表';

-- ----------------------------
-- 7. 浏览日志表
-- ----------------------------
drop table if exists tz_question_view_log;
create table tz_question_view_log (
  view_id          bigint(20) not null auto_increment comment '浏览ID',
  question_id      bigint(20) not null comment '问题ID',
  user_id          bigint(20) default null comment '浏览用户ID',
  ip_addr          varchar(128) default '' comment '访问IP',
  create_time      datetime comment '浏览时间',
  primary key (view_id),
  key idx_tz_view_question (question_id)
) engine=innodb comment='同舟问答浏览日志表';

-- ----------------------------
-- 8. 后台菜单
-- ----------------------------
insert into sys_menu values('2100', '同舟问答', '0', '5', 'tongzhou', null, '', '', 1, 0, 'M', '0', '0', '', 'education', 'admin', sysdate(), '', null, '同舟问答目录');
insert into sys_menu values('2101', '数据概览', '2100', '1', 'dashboard', 'tongzhou/dashboard/index', '', '', 1, 0, 'C', '0', '0', 'tongzhou:dashboard:view', 'dashboard', 'admin', sysdate(), '', null, '同舟问答概览');
insert into sys_menu values('2102', '分类管理', '2100', '2', 'category', 'tongzhou/category/index', '', '', 1, 0, 'C', '0', '0', 'tongzhou:category:list', 'tree-table', 'admin', sysdate(), '', null, '分类管理');
insert into sys_menu values('2103', '问题管理', '2100', '3', 'question', 'tongzhou/question/index', '', '', 1, 0, 'C', '0', '0', 'tongzhou:question:list', 'documentation', 'admin', sysdate(), '', null, '问题管理');
insert into sys_menu values('2104', '回答管理', '2100', '4', 'answer', 'tongzhou/answer/index', '', '', 1, 0, 'C', '0', '0', 'tongzhou:answer:list', 'message', 'admin', sysdate(), '', null, '回答管理');
insert into sys_menu values('2105', '用户档案', '2100', '5', 'user', 'tongzhou/user/index', '', '', 1, 0, 'C', '0', '0', 'tongzhou:user:list', 'user', 'admin', sysdate(), '', null, '前台用户档案');

insert into sys_menu values('2110', '分类查询', '2102', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'tongzhou:category:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2111', '分类新增', '2102', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'tongzhou:category:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2112', '分类修改', '2102', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'tongzhou:category:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2113', '分类删除', '2102', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'tongzhou:category:remove', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu values('2120', '问题详情', '2103', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'tongzhou:question:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2121', '问题审核', '2103', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'tongzhou:question:review', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2122', '问题下架', '2103', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'tongzhou:question:offline', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu values('2130', '回答详情', '2104', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'tongzhou:answer:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2131', '回答审核', '2104', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'tongzhou:answer:review', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2132', '回答下架', '2104', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'tongzhou:answer:offline', '#', 'admin', sysdate(), '', null, '');

-- 可选：给普通角色也开放查看能力，便于课程演示
insert into sys_role_menu values ('2', '2100');
insert into sys_role_menu values ('2', '2101');
insert into sys_role_menu values ('2', '2102');
insert into sys_role_menu values ('2', '2103');
insert into sys_role_menu values ('2', '2104');
insert into sys_role_menu values ('2', '2105');
insert into sys_role_menu values ('2', '2110');
insert into sys_role_menu values ('2', '2111');
insert into sys_role_menu values ('2', '2112');
insert into sys_role_menu values ('2', '2113');
insert into sys_role_menu values ('2', '2120');
insert into sys_role_menu values ('2', '2121');
insert into sys_role_menu values ('2', '2122');
insert into sys_role_menu values ('2', '2130');
insert into sys_role_menu values ('2', '2131');
insert into sys_role_menu values ('2', '2132');
