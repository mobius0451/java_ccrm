/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : db_ccrm

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2023-03-18 20:10:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_college
-- ----------------------------
DROP TABLE IF EXISTS `sys_college`;
CREATE TABLE `sys_college` (
  `college_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学院班级id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父学院班级id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `college_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '学院班级名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '班级状态（0正常 1停用）',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`college_id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';

-- ----------------------------
-- Records of sys_college
-- ----------------------------
INSERT INTO `sys_college` VALUES ('100', '0', '0', '广东疫控大学', '0', '什禾', '15888888888', 'sh@qq.com', '0', '0', 'admin', '2022-11-05 22:21:17', '', null, '');
INSERT INTO `sys_college` VALUES ('101', '100', '0,100', '互联网院', '1', '什禾', '15888888888', 'sh@qq.com', '0', '0', 'admin', '2022-11-05 22:21:17', '', null, '');
INSERT INTO `sys_college` VALUES ('102', '100', '0,100', '法学院', '2', '什禾', '15888888888', 'sh@qq.com', '0', '0', 'admin', '2022-11-05 22:21:17', '', null, '');
INSERT INTO `sys_college` VALUES ('103', '101', '0,100,101', '19软工1班', '1', '什禾', '15888888888', 'sh@qq.com', '0', '0', 'admin', '2022-11-05 22:21:17', '', null, '');
INSERT INTO `sys_college` VALUES ('104', '101', '0,100,101', '20软工1班', '2', '什禾', '15888888888', 'sh@qq.com', '0', '0', 'admin', '2022-11-05 22:21:17', '', null, '');
INSERT INTO `sys_college` VALUES ('105', '101', '0,100,101', '19计科1班', '3', '什禾', '15888888888', 'sh@qq.com', '0', '0', 'admin', '2022-11-05 22:21:17', '', null, '');
INSERT INTO `sys_college` VALUES ('107', '101', '0,100,101', '20计科1班', '5', '什禾', '15888888888', 'sh@qq.com', '0', '0', 'admin', '2022-11-05 22:21:17', '', null, '');
INSERT INTO `sys_college` VALUES ('108', '102', '0,100,102', '19法学1班', '1', '什禾', '15888888888', 'sh@qq.com', '0', '0', 'admin', '2022-11-05 22:21:17', '', null, '');
INSERT INTO `sys_college` VALUES ('109', '102', '0,100,102', '20法学1班', '2', '什禾', '15888888888', 'sh@qq.com', '0', '0', 'admin', '2022-11-05 22:21:17', '', null, '');
INSERT INTO `sys_college` VALUES ('201', '101', '0,100,101', '19大数据1班', '6', null, null, null, '0', '0', 'admin', '2022-11-22 11:59:34', null, null, '');
INSERT INTO `sys_college` VALUES ('202', '101', '0,100,101', '20大数据1班', '7', null, null, null, '0', '0', 'admin', '2022-11-22 12:00:04', null, null, '');

-- ----------------------------
-- Table structure for sys_enter
-- ----------------------------
DROP TABLE IF EXISTS `sys_enter`;
CREATE TABLE `sys_enter` (
  `enter_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '入校申请ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_college_id` bigint(20) NOT NULL,
  `enter_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '入校类型(0:临时访校, 1:出校过期 2:假期返校)',
  `enter_reason` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '入校具体原因',
  `departure_place` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '启程地',
  `begin_time` date NOT NULL COMMENT '入校时间',
  `over_time` date NOT NULL COMMENT '结束时间',
  `contact` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系方式',
  `emergency_contact` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '紧急联系',
  `healthy_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '健康码',
  `trip_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '行程码',
  `nucleic_acid` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '核酸证明',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '审核状态（0审核中 1审核通过 2拒绝申请）',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`enter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_enter
-- ----------------------------
INSERT INTO `sys_enter` VALUES ('1', '1', '101', '0', 'test1', '广东省/广州市/市辖区', '2022-12-06', '2022-12-06', '13688888888', null, '/profile/admin/8624a4a9-2d79-4c96-9748-acd385fd4574.jpg', '/profile/admin/737efe1e-5e56-4e29-858f-0100273ebcff.jpg', '/profile/admin/84501352-5dab-4c56-9043-873784ba1575.jpg', '0', '0', 'admin', '2022-12-06 17:24:08', 'admin', '2023-02-28 10:54:50', null);
INSERT INTO `sys_enter` VALUES ('2', '1', '101', '1', 'test2', '广东省/广州市/市辖区', '2022-12-07', '2022-12-07', '13577777777', null, '/profile/admin/f097c676-415b-431b-a787-1f7daeeb0e95.jpg', '/profile/admin/1a7dc8aa-ca79-4570-8ec1-a8b4c777cd1c.jpg', '/profile/admin/b915517c-c095-4c49-add7-22dfa0ca5bc3.jpg', '0', '0', 'admin', '2022-12-07 17:25:48', 'admin', '2023-02-28 10:55:05', null);
INSERT INTO `sys_enter` VALUES ('3', '1', '101', '2', 'admin test', '广东省/广州市/市辖区', '2022-12-01', '2022-12-01', '13577777777', null, '/profile/admin/419fc713-55c0-4759-ae36-caba9dbb8daa.jpg', '/profile/admin/993e5f10-6ea3-453b-b098-ae082ef0c7a3.jpg', '/profile/admin/7ea0b5ac-3148-42da-850d-738295ff024e.jpg', '0', '0', 'admin', '2022-12-01 01:26:52', 'admin', '2023-02-28 11:08:56', null);
INSERT INTO `sys_enter` VALUES ('4', '1', '100', '0', 'test', '浙江省/宁波市/市辖区', '2023-02-27', '2023-02-27', '15688888888', null, '/profile/admin/37098408-ee2a-424d-bbfd-8f80f01692c7.jpg', '/profile/admin/b6feb2e5-2ead-41e0-9700-9c0346047750.jpg', '/profile/admin/fff05b69-93eb-4957-b498-f6b990d1baee.jpg', '0', '0', 'admin', '2023-02-27 16:20:14', 'admin', '2023-02-28 11:09:26', null);
INSERT INTO `sys_enter` VALUES ('5', '1', '100', '0', 'test', '北京市/市辖区/东城区', '2023-02-27', '2023-02-27', '15677777777', null, '/profile/admin/79f43e25-29f2-44c6-97cd-a04282dcfeae.jpg', '/profile/admin/4817a8e2-e67e-4f52-b261-d5210ed63fda.jpg', '/profile/admin/7ca4b212-fc0a-48d2-bd60-9119eedf94ab.jpg', '0', '0', 'admin', '2023-02-27 18:35:07', 'admin', '2023-02-28 10:55:38', null);
INSERT INTO `sys_enter` VALUES ('6', '1', '100', '0', 'admin test', '山西省/太原市/市辖区', '2023-02-27', '2023-02-27', '18977777777', null, '/profile/admin/3c4eed2f-8379-43a5-9ab5-c60099dbc5e1.jpg', '/profile/admin/a06d8499-d48d-49a6-a4f1-dd4f687f376b.jpg', '/profile/admin/9a5f6bb6-6e1e-4e05-903a-cf2c34e17234.jpg', '0', '0', 'admin', '2023-02-28 02:56:31', 'admin', '2023-02-28 11:09:46', null);
INSERT INTO `sys_enter` VALUES ('8', '1', '100', '0', 'test', '浙江省/宁波市/海曙区', '2023-02-28', '2023-02-28', '12345678912', null, '/profile/admin/4b9da560-f1f1-465d-9d28-08181cd206cd.jpg', '/profile/admin/4b9da560-f1f1-465d-9d28-08181cd206cd.jpg', '/profile/admin/4b9da560-f1f1-465d-9d28-08181cd206cd.jpg', '0', '0', 'admin', '2023-02-28 11:14:21', null, null, null);
INSERT INTO `sys_enter` VALUES ('9', '1', '100', '0', 'test', '江西省/景德镇市/市辖区', '2023-02-28', '2023-02-28', '11111111111', null, '/profile/admin/f55a21fc-4855-444b-8892-c2357908a156.jpg', '/profile/admin/77a1d4d5-43da-4e4e-b5ee-fa048d7fc289.jpg', '/profile/admin/f07a8658-660b-4791-96aa-aa4feaa90561.jpg', '0', '0', 'admin', '2023-02-28 11:54:46', null, null, null);

-- ----------------------------
-- Table structure for sys_infected
-- ----------------------------
DROP TABLE IF EXISTS `sys_infected`;
CREATE TABLE `sys_infected` (
  `infected_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '感染记录ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `infected_span` int(10) NOT NULL DEFAULT '0' COMMENT '感染持续时间/天',
  `max_temperature` float(10,0) NOT NULL DEFAULT '36' COMMENT '感染期间最高体温',
  `infected_time` date DEFAULT NULL COMMENT '感染日期',
  `recovery_time` date DEFAULT NULL COMMENT '康复时间',
  `status` tinyint(1) NOT NULL COMMENT '状态（0：未康复， 1：已康复）',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`infected_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_infected
-- ----------------------------
INSERT INTO `sys_infected` VALUES ('1', '1', '7', '40', '2023-02-07', '2023-02-14', '1', '0', 'admin', '2023-02-07 16:09:58', '', null, null);
INSERT INTO `sys_infected` VALUES ('2', '1', '5', '39', '2023-02-13', '2023-02-18', '1', '0', 'admin', '2023-02-13 17:44:20', 'admin', '2023-02-18 13:26:35', null);
INSERT INTO `sys_infected` VALUES ('3', '1', '10', '40', '2023-02-18', '2023-02-28', '1', '0', 'admin', '2023-02-18 22:05:13', 'admin', '2023-02-28 16:31:57', null);
INSERT INTO `sys_infected` VALUES ('8', '1', '1', '39', '2023-03-08', null, '0', '0', 'admin', '2023-03-08 16:17:03', null, null, null);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '0', '1', 'system', null, '', '1', '0', 'M', '0', '0', '', 'system', 'admin', '2022-11-05 22:21:20', '', null, '系统管理目录');
INSERT INTO `sys_menu` VALUES ('2', '用户管理', '1', '1', 'user', 'system/user/index', '', '1', '0', 'C', '0', '0', 'system:user:list', 'user', 'admin', '2022-11-05 22:21:20', '', null, '用户管理菜单');
INSERT INTO `sys_menu` VALUES ('3', '用户查询', '2', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:user:query', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('4', '用户新增', '2', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:user:add', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('5', '用户修改', '2', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:user:edit', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('6', '用户删除', '2', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:user:remove', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('7', '用户导出', '2', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:user:export', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('8', '用户导入', '2', '6', '', '', '', '1', '0', 'F', '0', '0', 'system:user:import', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('9', '重置密码', '2', '7', '', '', '', '1', '0', 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2022-11-04 14:21:20', 'admin', '2023-03-02 08:52:59', '');
INSERT INTO `sys_menu` VALUES ('10', '角色管理', '1', '2', 'role', 'system/role/index', '', '1', '0', 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2022-11-05 22:21:20', '', null, '角色管理菜单');
INSERT INTO `sys_menu` VALUES ('11', '角色查询', '10', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:role:query', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('12', '角色新增', '10', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:role:add', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('13', '角色修改', '10', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:role:edit', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('14', '角色删除', '10', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:role:remove', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('15', '角色导出', '10', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:role:export', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('16', '菜单管理', '1', '3', 'menu', 'system/menu/index', '', '1', '0', 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2022-11-05 22:21:20', '', null, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES ('17', '菜单查询', '16', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:query', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('18', '菜单新增', '16', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:add', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('19', '菜单修改', '16', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('20', '菜单删除', '16', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('21', '班级管理', '1', '4', 'dept', 'system/college/index', '', '1', '0', 'C', '0', '0', 'system:college:list', 'tree', 'admin', '2022-11-05 22:21:20', '', null, '部门管理菜单');
INSERT INTO `sys_menu` VALUES ('22', '班级查询', '21', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:college:query', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('23', '班级新增', '21', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:college:add', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('24', '班级修改', '21', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:college:edit', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('25', '班级删除', '21', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:college:remove', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('26', '通知公告', '1', '5', 'notice', 'system/notice/index', '', '1', '0', 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2022-11-05 14:21:20', 'admin', '2023-02-12 06:44:08', '通知公告菜单');
INSERT INTO `sys_menu` VALUES ('27', '公告查询', '26', '1', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:query', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('28', '公告新增', '26', '2', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:add', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('29', '公告修改', '26', '3', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('30', '公告删除', '26', '4', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2022-11-05 22:21:20', '', null, '');
INSERT INTO `sys_menu` VALUES ('31', '出入管理', '0', '2', 'access', null, '', '1', '0', 'M', '0', '0', '', 'guide', 'admin', '2022-11-05 06:21:20', 'admin', '2023-02-13 09:25:07', '系统监控目录');
INSERT INTO `sys_menu` VALUES ('32', '出校管理', '31', '1', 'out', 'access/out/index', null, '1', '0', 'C', '0', '0', 'access:out:list', 'edit', 'admin', '2022-11-22 21:02:41', '', null, '');
INSERT INTO `sys_menu` VALUES ('33', '入校管理', '31', '2', 'enter', 'access/enter/index', null, '1', '0', 'C', '0', '0', 'access:enter:list', 'link', 'admin', '2022-11-21 13:04:09', 'admin', '2023-02-27 14:12:56', '');
INSERT INTO `sys_menu` VALUES ('34', '健康管理', '0', '3', 'healthy', null, '', '1', '0', 'M', '0', '0', '', 'logininfor', 'admin', '2022-11-03 22:21:20', 'admin', '2023-02-19 10:09:23', '系统工具目录');
INSERT INTO `sys_menu` VALUES ('35', '健康报备', '34', '1', 'report', 'healthy/report/index', null, '1', '0', 'C', '0', '0', 'healthy:report:list', 'checkbox', 'admin', '2022-11-21 05:14:02', 'admin', '2023-02-12 06:50:59', '');
INSERT INTO `sys_menu` VALUES ('36', '感染记录', '34', '3', 'infected', 'healthy/infected/index', null, '1', '0', 'C', '0', '0', 'healthy:infected:list', 'date', 'admin', '2022-11-21 13:17:03', 'admin', '2023-02-12 09:06:42', '');
INSERT INTO `sys_menu` VALUES ('37', '疫苗记录', '34', '3', 'vaccines', 'healthy/vaccines/index', null, '1', '0', 'C', '0', '0', 'healthy:vaccines:list', 'build', 'admin', '2022-11-20 13:18:57', 'admin', '2023-02-12 09:24:03', '');
INSERT INTO `sys_menu` VALUES ('38', '重症管理', '0', '4', 'serious', null, null, '1', '0', 'M', '0', '0', null, 'chart', 'admin', '2022-11-20 21:20:22', 'admin', '2023-02-13 09:25:01', '');
INSERT INTO `sys_menu` VALUES ('39', '重症登记', '38', '1', 'info', 'serious/info/index', null, '1', '0', 'C', '0', '0', 'serious:info:list', 'question', 'admin', '2022-11-21 05:22:26', 'admin', '2023-02-19 10:02:23', '');
INSERT INTO `sys_menu` VALUES ('40', '人员管理', '38', '2', 'people', 'serious/people/index', null, '1', '0', 'C', '0', '0', 'serious:people:list', 'people', 'admin', '2022-11-21 13:23:59', 'admin', '2023-02-21 13:31:42', '');
INSERT INTO `sys_menu` VALUES ('41', '信息统计', '0', '5', 'statistics', null, null, '1', '0', 'M', '0', '0', null, 'monitor', 'admin', '2023-02-11 22:41:31', 'admin', '2023-02-13 09:24:53', '');
INSERT INTO `sys_menu` VALUES ('42', '测试文档', '0', '6', 'http://localhost:8806/doc.html', null, null, '0', '0', 'M', '0', '0', null, 'bug', 'admin', '2022-11-27 14:11:21', 'admin', '2023-02-12 06:41:55', '');
INSERT INTO `sys_menu` VALUES ('43', '出校查询', '32', '1', '', null, null, '1', '0', 'F', '0', '0', 'access:out:query', '#', 'admin', '2023-02-12 17:26:30', 'admin', '2023-02-13 09:35:58', '');
INSERT INTO `sys_menu` VALUES ('44', '申请出校', '32', '2', '', null, null, '1', '0', 'F', '0', '0', 'access:out:add', '#', 'admin', '2023-02-12 09:26:51', 'admin', '2023-02-13 09:36:05', '');
INSERT INTO `sys_menu` VALUES ('45', '删除出校', '32', '3', '', null, null, '1', '0', 'F', '0', '0', 'access:out:remove', '#', 'admin', '2023-02-12 09:27:10', 'admin', '2023-02-13 09:36:14', '');
INSERT INTO `sys_menu` VALUES ('46', '修改出校', '32', '4', '', null, null, '1', '0', 'F', '0', '0', 'access:out:edit', '#', 'admin', '2023-02-12 17:27:26', 'admin', '2023-02-13 09:36:23', '');
INSERT INTO `sys_menu` VALUES ('47', '入校查询', '33', '1', '', null, null, '1', '0', 'F', '0', '0', 'access:enter:query', '#', 'admin', '2023-02-12 17:28:09', 'admin', '2023-02-13 09:36:40', '');
INSERT INTO `sys_menu` VALUES ('48', '入校申请', '33', '2', '', null, null, '1', '0', 'F', '0', '0', 'access:enter:add', '#', 'admin', '2023-02-12 17:28:27', 'admin', '2023-02-13 09:36:46', '');
INSERT INTO `sys_menu` VALUES ('49', '删除入校', '33', '3', '', null, null, '1', '0', 'F', '0', '0', 'access:enter:remove', '#', 'admin', '2023-02-12 17:28:39', 'admin', '2023-02-13 09:36:53', '');
INSERT INTO `sys_menu` VALUES ('50', '修改入校', '33', '4', '', null, null, '1', '0', 'F', '0', '0', 'access:enter:edit', '#', 'admin', '2023-02-12 17:28:52', 'admin', '2023-02-13 09:36:59', '');
INSERT INTO `sys_menu` VALUES ('51', '出校审核', '32', '5', '', null, null, '1', '0', 'F', '0', '0', 'access:out:check', '#', 'admin', '2023-02-12 17:32:32', 'admin', '2023-02-28 06:36:10', '');
INSERT INTO `sys_menu` VALUES ('52', '入校审核', '33', '5', '', null, null, '1', '0', 'F', '0', '0', 'access:enter:check', '#', 'admin', '2023-02-12 17:33:13', 'admin', '2023-02-28 06:39:12', '');
INSERT INTO `sys_menu` VALUES ('53', '新增报备', '35', '1', '', null, null, '1', '0', 'F', '0', '0', 'healthy:report:add', '#', 'admin', '2023-02-13 14:01:29', null, null, '');
INSERT INTO `sys_menu` VALUES ('54', '个人统计', '41', '1', 'personal', 'statistics/personal/index', null, '1', '0', 'C', '0', '0', 'statistics:personal:query', 'rate', 'admin', '2023-02-19 02:03:41', 'admin', '2023-02-19 10:04:56', '');
INSERT INTO `sys_menu` VALUES ('55', '出入统计', '41', '2', 'entry&out', 'statistics/entry&out/index', null, '1', '0', 'C', '0', '0', 'statistics:entry&out:query', 'drag', 'admin', '2023-02-19 02:06:29', 'admin', '2023-02-19 10:09:01', '');
INSERT INTO `sys_menu` VALUES ('56', '健康统计', '41', '3', 'health', 'statistics/health/index', null, '1', '0', 'C', '0', '0', 'statistics:health:query', 'cascader', 'admin', '2023-02-19 02:07:18', 'admin', '2023-02-19 10:10:06', '');
INSERT INTO `sys_menu` VALUES ('57', '重症统计', '41', '4', 'severe', 'statistics/severe/index', null, '1', '0', 'C', '0', '0', 'statistics:severe:query', 'tool', 'admin', '2023-02-19 02:08:42', 'admin', '2023-02-19 10:10:34', '');
INSERT INTO `sys_menu` VALUES ('59', '用户健康', '34', '4', 'userHealthy', 'healthy/userHealthy/index', null, '1', '0', 'C', '0', '0', 'healthy:userHealthy:list', 'form', 'admin', '2023-02-21 14:09:27', 'admin', '2023-02-22 06:10:37', '');
INSERT INTO `sys_menu` VALUES ('60', '取消审核', '32', '6', '', null, null, '1', '0', 'F', '0', '0', 'access:out:noCheck', '#', 'admin', '2023-02-28 06:38:39', null, null, '');
INSERT INTO `sys_menu` VALUES ('61', '取消审核', '33', '6', '', null, null, '1', '0', 'F', '0', '0', 'access:enter:noCheck', '#', 'admin', '2023-02-28 06:39:40', null, null, '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `notice_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL DEFAULT '1' COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `picture` varchar(100) DEFAULT NULL COMMENT '公告图片',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知公告表';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES ('1', '测试公告1', '2', 0x3C68313E74657374313C2F68313E, null, '0', 'admin', '2022-11-04 22:21:26', 'admin', '2023-02-28 09:50:34', '管理员');
INSERT INTO `sys_notice` VALUES ('2', '测试通知1', '1', 0x3C703E74657374323C2F703E, null, '0', 'admin', '2022-11-05 06:21:26', 'admin', '2023-02-28 09:50:58', '管理员');
INSERT INTO `sys_notice` VALUES ('10', '测试公告2', '2', null, null, '0', 'admin', '2023-02-19 00:05:24', 'admin', '2023-02-19 08:06:01', null);
INSERT INTO `sys_notice` VALUES ('11', '测试公告3', '2', null, null, '0', 'admin', '2023-02-19 00:05:40', 'admin', '2023-02-19 08:05:55', null);
INSERT INTO `sys_notice` VALUES ('12', '测试通知2', '1', 0x3C703E3C62723E3C2F703E, null, '0', 'admin', '2023-02-19 08:06:33', null, null, null);
INSERT INTO `sys_notice` VALUES ('13', '测试通知3', '1', null, null, '0', 'admin', '2023-02-19 08:06:42', null, null, null);
INSERT INTO `sys_notice` VALUES ('14', '测试公告4', '2', null, '/profile/admin/b1c04f45-6ea3-44dc-9b73-6bc7f3880f79.jpg', '0', 'admin', '2023-02-18 16:47:49', 'admin', '2023-03-08 14:01:57', null);
INSERT INTO `sys_notice` VALUES ('15', '测试公告5', '2', null, '/profile/admin/041ed5eb-1efc-4169-9a0a-37f838b24584.jpg', '0', 'admin', '2023-02-18 16:47:54', 'admin', '2023-03-08 14:01:31', null);
INSERT INTO `sys_notice` VALUES ('16', '测试公告6', '2', null, '/profile/admin/98273682-0b4b-4b41-acb3-010781912bdc.png', '0', 'admin', '2023-02-18 16:48:01', 'admin', '2023-03-08 13:39:52', null);
INSERT INTO `sys_notice` VALUES ('21', '测试通知4', '1', 0x3C703E7465737430343C2F703E, '/profile/admin/31ee9e63-32ab-4aec-b026-7082e0df3787.png', '0', 'admin', '2023-02-18 00:49:14', 'admin', '2023-03-08 13:51:49', null);
INSERT INTO `sys_notice` VALUES ('22', '测试通知5', '1', null, null, '0', 'admin', '2023-02-19 00:49:19', 'admin', '2023-02-19 08:49:32', null);
INSERT INTO `sys_notice` VALUES ('23', '测试通知6', '1', 0x3C703E74657374363C2F703E, '/profile/admin/5fd2c7aa-7c01-43a8-a313-15997e23cfe5.png', '0', 'admin', '2023-02-18 08:49:37', 'admin', '2023-03-08 13:47:54', null);
INSERT INTO `sys_notice` VALUES ('31', '测试公告7', '2', 0x3C703E746573743C2F703E, '/profile/admin/5330f8e5-0853-416c-89ff-bef5a789466b.png', '0', 'admin', '2023-02-28 03:32:21', 'admin', '2023-03-08 13:39:11', null);
INSERT INTO `sys_notice` VALUES ('32', '测试公告8', '2', 0x3C703E746573743C2F703E, '/profile/admin/97522ef0-27d6-4cc7-986f-5ce538b23ad1.png', '0', 'admin', '2023-02-27 11:44:40', 'admin', '2023-03-08 13:39:22', null);

-- ----------------------------
-- Table structure for sys_out
-- ----------------------------
DROP TABLE IF EXISTS `sys_out`;
CREATE TABLE `sys_out` (
  `out_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '出校申请ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_college_id` bigint(20) NOT NULL COMMENT '用户所属学院班级id',
  `out_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '出校类型(0:临时出校, 1:固定出校)',
  `out_reason` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '出校原因',
  `out_where` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '出校去向',
  `begin_time` date NOT NULL COMMENT '出校时间',
  `over_time` date NOT NULL COMMENT '结束时间',
  `contact` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系方式',
  `emergency_contact` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '紧急联系',
  `healthy_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '健康码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '审核状态（0审核中 1审核通过 2拒绝申请）',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`out_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_out
-- ----------------------------
INSERT INTO `sys_out` VALUES ('1', '1', '101', '0', 'test1', '广东省/广州市/市辖区', '2022-11-08', '2022-11-08', '13688888888', null, '/profile/admin/a50c9ab2-fd26-4b65-be7b-03ae7f4b7e74.jpg', '0', '0', 'admin', '2022-11-08 05:35:01', 'admin', '2023-02-28 10:45:51', null);
INSERT INTO `sys_out` VALUES ('2', '1', '101', '1', 'test2', '北京市/市辖区/东城区', '2022-11-23', '2022-11-23', '13577777777', null, '/profile/admin/377c0f7b-c35e-4763-8b0e-2e80453b00d0.jpg', '0', '0', 'admin', '2022-11-24 06:23:50', 'admin', '2023-02-28 10:46:55', null);
INSERT INTO `sys_out` VALUES ('3', '1', '101', '0', 'test3', '河北省/石家庄市/长安区', '2022-11-26', '2022-11-26', '13577777777', null, '/profile/admin/862cd68d-e215-4d6f-9a07-85a4a6385b9a.jpg', '0', '0', 'admin', '2022-11-26 22:26:04', 'admin', '2023-02-28 11:03:07', null);
INSERT INTO `sys_out` VALUES ('5', '6', '103', '0', 'student test', '江西省/南昌市/市辖区', '2023-02-25', '2023-02-25', '13544444444', null, '/profile/admin/7b9ab394-1b4e-4380-8ead-62488197744a.jpg', '0', '0', 'student', '2023-02-26 06:23:02', 'admin', '2023-02-28 10:53:33', null);
INSERT INTO `sys_out` VALUES ('6', '6', '103', '0', 'student test', '浙江省/宁波市/市辖区', '2023-02-26', '2023-02-26', '13544444444', null, '/profile/student/59509a30-0512-47a5-bfcc-eda3089aedad.jpg', '0', '0', 'student', '2023-02-27 06:25:43', 'student', '2023-02-27 15:58:44', null);
INSERT INTO `sys_out` VALUES ('7', '1', '100', '0', null, '浙江省/宁波市/市辖区', '2023-02-28', '2023-02-28', '13866666666', null, '/profile/admin/null', '1', '0', 'admin', '2023-02-28 08:01:37', null, null, null);
INSERT INTO `sys_out` VALUES ('8', '1', '100', '0', null, '江西省/南昌市/市辖区', '2023-02-28', '2023-02-28', '15677777777', null, '/profile/admin/null', '1', '0', 'admin', '2023-02-28 08:03:11', null, null, null);
INSERT INTO `sys_out` VALUES ('9', '1', '100', '0', null, '江西省/南昌市/市辖区', '2023-02-28', '2023-02-28', '15677777777', null, '/profile/admin/null', '1', '0', 'admin', '2023-02-28 08:03:43', null, null, null);
INSERT INTO `sys_out` VALUES ('10', '6', '103', '0', null, '江苏省/南京市/市辖区', '2023-02-28', '2023-02-28', '13544444444', null, '/profile/student/null', '1', '0', 'student', '2023-02-28 08:32:45', null, null, null);
INSERT INTO `sys_out` VALUES ('11', '1', '100', '0', null, '江苏省/南京市/市辖区', '2023-03-01', '2023-03-02', '15699999999', null, '/profile/admin/null', '1', '0', 'admin', '2023-03-01 14:37:57', null, null, null);
INSERT INTO `sys_out` VALUES ('12', '6', '103', '0', null, '江西省/南昌市/市辖区', '2023-02-28', '2023-03-01', '13244444444', null, '/profile/student/null', '1', '1', 'student', '2023-03-02 07:58:54', null, null, null);
INSERT INTO `sys_out` VALUES ('13', '1', '100', '0', null, '江西省/景德镇市/昌江区', '2023-03-09', '2023-03-10', '13455555555', null, '/profile/admin/null', '1', '0', 'admin', '2023-03-09 16:47:10', null, null, null);

-- ----------------------------
-- Table structure for sys_report
-- ----------------------------
DROP TABLE IF EXISTS `sys_report`;
CREATE TABLE `sys_report` (
  `report_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报备ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `body_condition` tinyint(1) NOT NULL DEFAULT '0' COMMENT '身体状况（0：正常， 1：发热咳嗽， 2：其他不正常状况）',
  `temperature` double(10,0) NOT NULL COMMENT '体温',
  `testing` tinyint(1) NOT NULL DEFAULT '0' COMMENT '抗原或核酸检测情况是否异常（0：否， 1：是）',
  `touch` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否接触新冠阳性患者（0：否， 1：是）',
  `location` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '报备位置',
  `report_time` date DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_report
-- ----------------------------
INSERT INTO `sys_report` VALUES ('1', '1', '0', '36', '0', '0', '广东省/珠海市/金湾区', null, '0', 'admin', '2023-02-01 07:13:44', null, null, null);
INSERT INTO `sys_report` VALUES ('5', '1', '0', '36', '0', '0', '北京市/市辖区/东城区', null, '0', 'admin', '2023-02-02 20:10:04', null, null, null);
INSERT INTO `sys_report` VALUES ('6', '1', '0', '36', '0', '0', '江西省/南昌市/市辖区', null, '0', 'admin', '2023-02-03 17:28:04', null, null, null);
INSERT INTO `sys_report` VALUES ('7', '1', '0', '36', '0', '0', '江西省/南昌市/市辖区', null, '0', 'admin', '2023-02-04 01:34:31', null, null, null);
INSERT INTO `sys_report` VALUES ('8', '1', '1', '38', '1', '0', '江西省/南昌市/市辖区', null, '0', 'admin', '2023-02-05 09:41:38', null, null, null);
INSERT INTO `sys_report` VALUES ('9', '1', '1', '38', '1', '0', '江西省/南昌市/市辖区', null, '0', 'admin', '2023-02-06 01:42:25', null, null, null);
INSERT INTO `sys_report` VALUES ('10', '1', '1', '39', '1', '0', '江西省/南昌市/市辖区', null, '0', 'admin', '2023-02-14 13:29:38', null, null, null);
INSERT INTO `sys_report` VALUES ('11', '1', '0', '36', '0', '0', '广东省/广州市/市辖区', null, '0', 'admin', '2023-02-15 21:47:49', null, null, null);
INSERT INTO `sys_report` VALUES ('12', '1', '0', '36', '0', '0', '浙江省/杭州市/市辖区', null, '0', 'admin', '2023-02-07 13:26:35', null, null, null);
INSERT INTO `sys_report` VALUES ('13', '1', '0', '36', '0', '0', '江苏省/南京市/市辖区', null, '0', 'admin', '2023-02-08 13:46:37', null, null, null);
INSERT INTO `sys_report` VALUES ('14', '1', '1', '40', '1', '1', '上海市/市辖区/杨浦区', null, '0', 'admin', '2023-02-09 13:53:58', null, null, null);
INSERT INTO `sys_report` VALUES ('17', '1', '1', '40', '1', '1', '上海市/市辖区/黄浦区', null, '0', 'admin', '2023-02-10 14:05:13', null, null, null);
INSERT INTO `sys_report` VALUES ('18', '1', '1', '39', '1', '0', '黑龙江省/哈尔滨市/市辖区', null, '0', 'admin', '2023-02-11 14:07:25', null, null, null);
INSERT INTO `sys_report` VALUES ('19', '1', '0', '36', '0', '0', '浙江省/杭州市/市辖区', '2023-03-01', '0', 'admin', '2023-03-01 16:31:57', null, null, null);
INSERT INTO `sys_report` VALUES ('24', '1', '0', '36', '0', '0', '北京市/市辖区/东城区', '2023-03-02', '0', 'admin', '2023-03-02 07:13:27', null, null, null);
INSERT INTO `sys_report` VALUES ('26', '1', '0', '36', '0', '0', '江苏省/南京市/市辖区', '2023-03-06', '0', 'admin', '2023-03-07 11:17:37', null, null, null);
INSERT INTO `sys_report` VALUES ('27', '1', '0', '39', '1', '1', '江苏省/南京市/市辖区', '2023-03-06', '0', 'admin', '2023-03-08 05:36:03', 'admin', '2023-03-08 13:36:44', null);
INSERT INTO `sys_report` VALUES ('28', '1', '0', '39', '1', '0', '浙江省/杭州市/市辖区', '2023-03-08', '0', 'admin', '2023-03-09 08:11:14', 'admin', '2023-03-08 16:17:03', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '角色状态（0正常 1停用）',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本班级数据权限 4：本班级及以下数据权限）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin', '1', '1', '0', '0', 'admin', '2022-11-05 22:21:19', '', null, '超级管理员');
INSERT INTO `sys_role` VALUES ('2', '维护人员', 'common', '2', '1', '0', '0', 'admin', '2022-11-03 06:21:19', 'admin', '2023-03-02 08:53:19', '维护人员');
INSERT INTO `sys_role` VALUES ('3', 'test111', 'test111', '3', '0', '0', '1', 'admin', null, 'admin', null, 'test111');
INSERT INTO `sys_role` VALUES ('4', 'test222', 'test222', '4', '0', '0', '1', 'admin', null, 'admin', null, 'test222');
INSERT INTO `sys_role` VALUES ('5', '测试人员', 'test', '3', '1', '0', '0', 'sh', '2022-11-21 01:30:18', 'admin', '2023-03-02 08:53:28', null);
INSERT INTO `sys_role` VALUES ('6', '学生', 'student', '4', '0', '0', '0', 'admin', '2022-11-19 12:50:14', 'admin', '2023-03-02 08:53:48', null);
INSERT INTO `sys_role` VALUES ('7', '教师', 'teacher', '5', '1', '0', '0', 'admin', '2022-11-20 04:50:53', 'admin', '2023-03-08 15:47:00', null);
INSERT INTO `sys_role` VALUES ('8', '辅导员', 'instructor', '6', '1', '0', '0', 'admin', '2022-11-21 12:51:23', 'admin', '2023-02-12 09:09:15', null);
INSERT INTO `sys_role` VALUES ('9', '校内工作者', 'worker', '7', '1', '0', '0', 'admin', '2022-11-22 12:52:42', null, null, null);
INSERT INTO `sys_role` VALUES ('10', '门卫保安', 'guard', '8', '1', '0', '0', 'admin', '2022-11-22 12:54:23', null, null, null);
INSERT INTO `sys_role` VALUES ('11', '外访人员', 'foreign', '9', '1', '0', '0', 'admin', '2022-11-22 04:55:58', 'admin', '2022-11-22 12:56:09', null);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '2');
INSERT INTO `sys_role_menu` VALUES ('2', '3');
INSERT INTO `sys_role_menu` VALUES ('2', '4');
INSERT INTO `sys_role_menu` VALUES ('2', '5');
INSERT INTO `sys_role_menu` VALUES ('2', '6');
INSERT INTO `sys_role_menu` VALUES ('2', '7');
INSERT INTO `sys_role_menu` VALUES ('2', '8');
INSERT INTO `sys_role_menu` VALUES ('2', '9');
INSERT INTO `sys_role_menu` VALUES ('2', '10');
INSERT INTO `sys_role_menu` VALUES ('2', '11');
INSERT INTO `sys_role_menu` VALUES ('2', '12');
INSERT INTO `sys_role_menu` VALUES ('2', '13');
INSERT INTO `sys_role_menu` VALUES ('2', '14');
INSERT INTO `sys_role_menu` VALUES ('2', '15');
INSERT INTO `sys_role_menu` VALUES ('2', '16');
INSERT INTO `sys_role_menu` VALUES ('2', '17');
INSERT INTO `sys_role_menu` VALUES ('2', '18');
INSERT INTO `sys_role_menu` VALUES ('2', '19');
INSERT INTO `sys_role_menu` VALUES ('2', '20');
INSERT INTO `sys_role_menu` VALUES ('2', '21');
INSERT INTO `sys_role_menu` VALUES ('2', '22');
INSERT INTO `sys_role_menu` VALUES ('2', '23');
INSERT INTO `sys_role_menu` VALUES ('2', '24');
INSERT INTO `sys_role_menu` VALUES ('2', '25');
INSERT INTO `sys_role_menu` VALUES ('2', '26');
INSERT INTO `sys_role_menu` VALUES ('2', '27');
INSERT INTO `sys_role_menu` VALUES ('2', '28');
INSERT INTO `sys_role_menu` VALUES ('2', '29');
INSERT INTO `sys_role_menu` VALUES ('2', '30');
INSERT INTO `sys_role_menu` VALUES ('2', '31');
INSERT INTO `sys_role_menu` VALUES ('2', '32');
INSERT INTO `sys_role_menu` VALUES ('2', '33');
INSERT INTO `sys_role_menu` VALUES ('2', '34');
INSERT INTO `sys_role_menu` VALUES ('2', '35');
INSERT INTO `sys_role_menu` VALUES ('2', '36');
INSERT INTO `sys_role_menu` VALUES ('2', '37');
INSERT INTO `sys_role_menu` VALUES ('2', '38');
INSERT INTO `sys_role_menu` VALUES ('2', '39');
INSERT INTO `sys_role_menu` VALUES ('2', '40');
INSERT INTO `sys_role_menu` VALUES ('2', '41');
INSERT INTO `sys_role_menu` VALUES ('2', '42');
INSERT INTO `sys_role_menu` VALUES ('2', '43');
INSERT INTO `sys_role_menu` VALUES ('2', '44');
INSERT INTO `sys_role_menu` VALUES ('2', '45');
INSERT INTO `sys_role_menu` VALUES ('2', '46');
INSERT INTO `sys_role_menu` VALUES ('2', '47');
INSERT INTO `sys_role_menu` VALUES ('2', '48');
INSERT INTO `sys_role_menu` VALUES ('2', '49');
INSERT INTO `sys_role_menu` VALUES ('2', '50');
INSERT INTO `sys_role_menu` VALUES ('2', '51');
INSERT INTO `sys_role_menu` VALUES ('2', '52');
INSERT INTO `sys_role_menu` VALUES ('2', '53');
INSERT INTO `sys_role_menu` VALUES ('2', '54');
INSERT INTO `sys_role_menu` VALUES ('2', '55');
INSERT INTO `sys_role_menu` VALUES ('2', '56');
INSERT INTO `sys_role_menu` VALUES ('2', '57');
INSERT INTO `sys_role_menu` VALUES ('2', '59');
INSERT INTO `sys_role_menu` VALUES ('2', '60');
INSERT INTO `sys_role_menu` VALUES ('2', '61');
INSERT INTO `sys_role_menu` VALUES ('5', '1');
INSERT INTO `sys_role_menu` VALUES ('5', '2');
INSERT INTO `sys_role_menu` VALUES ('5', '3');
INSERT INTO `sys_role_menu` VALUES ('5', '4');
INSERT INTO `sys_role_menu` VALUES ('5', '5');
INSERT INTO `sys_role_menu` VALUES ('5', '6');
INSERT INTO `sys_role_menu` VALUES ('5', '7');
INSERT INTO `sys_role_menu` VALUES ('5', '8');
INSERT INTO `sys_role_menu` VALUES ('5', '9');
INSERT INTO `sys_role_menu` VALUES ('5', '10');
INSERT INTO `sys_role_menu` VALUES ('5', '11');
INSERT INTO `sys_role_menu` VALUES ('5', '12');
INSERT INTO `sys_role_menu` VALUES ('5', '13');
INSERT INTO `sys_role_menu` VALUES ('5', '14');
INSERT INTO `sys_role_menu` VALUES ('5', '15');
INSERT INTO `sys_role_menu` VALUES ('5', '16');
INSERT INTO `sys_role_menu` VALUES ('5', '17');
INSERT INTO `sys_role_menu` VALUES ('5', '18');
INSERT INTO `sys_role_menu` VALUES ('5', '19');
INSERT INTO `sys_role_menu` VALUES ('5', '20');
INSERT INTO `sys_role_menu` VALUES ('5', '21');
INSERT INTO `sys_role_menu` VALUES ('5', '22');
INSERT INTO `sys_role_menu` VALUES ('5', '23');
INSERT INTO `sys_role_menu` VALUES ('5', '24');
INSERT INTO `sys_role_menu` VALUES ('5', '25');
INSERT INTO `sys_role_menu` VALUES ('5', '26');
INSERT INTO `sys_role_menu` VALUES ('5', '27');
INSERT INTO `sys_role_menu` VALUES ('5', '28');
INSERT INTO `sys_role_menu` VALUES ('5', '29');
INSERT INTO `sys_role_menu` VALUES ('5', '30');
INSERT INTO `sys_role_menu` VALUES ('5', '31');
INSERT INTO `sys_role_menu` VALUES ('5', '32');
INSERT INTO `sys_role_menu` VALUES ('5', '33');
INSERT INTO `sys_role_menu` VALUES ('5', '34');
INSERT INTO `sys_role_menu` VALUES ('5', '35');
INSERT INTO `sys_role_menu` VALUES ('5', '36');
INSERT INTO `sys_role_menu` VALUES ('5', '37');
INSERT INTO `sys_role_menu` VALUES ('5', '38');
INSERT INTO `sys_role_menu` VALUES ('5', '39');
INSERT INTO `sys_role_menu` VALUES ('5', '40');
INSERT INTO `sys_role_menu` VALUES ('5', '41');
INSERT INTO `sys_role_menu` VALUES ('5', '42');
INSERT INTO `sys_role_menu` VALUES ('5', '43');
INSERT INTO `sys_role_menu` VALUES ('5', '44');
INSERT INTO `sys_role_menu` VALUES ('5', '45');
INSERT INTO `sys_role_menu` VALUES ('5', '46');
INSERT INTO `sys_role_menu` VALUES ('5', '47');
INSERT INTO `sys_role_menu` VALUES ('5', '48');
INSERT INTO `sys_role_menu` VALUES ('5', '49');
INSERT INTO `sys_role_menu` VALUES ('5', '50');
INSERT INTO `sys_role_menu` VALUES ('5', '51');
INSERT INTO `sys_role_menu` VALUES ('5', '52');
INSERT INTO `sys_role_menu` VALUES ('5', '53');
INSERT INTO `sys_role_menu` VALUES ('5', '54');
INSERT INTO `sys_role_menu` VALUES ('5', '55');
INSERT INTO `sys_role_menu` VALUES ('5', '56');
INSERT INTO `sys_role_menu` VALUES ('5', '57');
INSERT INTO `sys_role_menu` VALUES ('5', '59');
INSERT INTO `sys_role_menu` VALUES ('5', '60');
INSERT INTO `sys_role_menu` VALUES ('5', '61');
INSERT INTO `sys_role_menu` VALUES ('6', '9');
INSERT INTO `sys_role_menu` VALUES ('6', '27');
INSERT INTO `sys_role_menu` VALUES ('6', '31');
INSERT INTO `sys_role_menu` VALUES ('6', '32');
INSERT INTO `sys_role_menu` VALUES ('6', '33');
INSERT INTO `sys_role_menu` VALUES ('6', '34');
INSERT INTO `sys_role_menu` VALUES ('6', '35');
INSERT INTO `sys_role_menu` VALUES ('6', '36');
INSERT INTO `sys_role_menu` VALUES ('6', '37');
INSERT INTO `sys_role_menu` VALUES ('6', '38');
INSERT INTO `sys_role_menu` VALUES ('6', '39');
INSERT INTO `sys_role_menu` VALUES ('6', '41');
INSERT INTO `sys_role_menu` VALUES ('6', '43');
INSERT INTO `sys_role_menu` VALUES ('6', '44');
INSERT INTO `sys_role_menu` VALUES ('6', '45');
INSERT INTO `sys_role_menu` VALUES ('6', '46');
INSERT INTO `sys_role_menu` VALUES ('6', '47');
INSERT INTO `sys_role_menu` VALUES ('6', '48');
INSERT INTO `sys_role_menu` VALUES ('6', '49');
INSERT INTO `sys_role_menu` VALUES ('6', '50');
INSERT INTO `sys_role_menu` VALUES ('6', '53');
INSERT INTO `sys_role_menu` VALUES ('6', '54');
INSERT INTO `sys_role_menu` VALUES ('6', '59');
INSERT INTO `sys_role_menu` VALUES ('6', '60');
INSERT INTO `sys_role_menu` VALUES ('6', '61');
INSERT INTO `sys_role_menu` VALUES ('7', '1');
INSERT INTO `sys_role_menu` VALUES ('7', '2');
INSERT INTO `sys_role_menu` VALUES ('7', '3');
INSERT INTO `sys_role_menu` VALUES ('7', '7');
INSERT INTO `sys_role_menu` VALUES ('7', '8');
INSERT INTO `sys_role_menu` VALUES ('7', '9');
INSERT INTO `sys_role_menu` VALUES ('7', '21');
INSERT INTO `sys_role_menu` VALUES ('7', '22');
INSERT INTO `sys_role_menu` VALUES ('7', '26');
INSERT INTO `sys_role_menu` VALUES ('7', '27');
INSERT INTO `sys_role_menu` VALUES ('7', '28');
INSERT INTO `sys_role_menu` VALUES ('7', '29');
INSERT INTO `sys_role_menu` VALUES ('7', '30');
INSERT INTO `sys_role_menu` VALUES ('7', '31');
INSERT INTO `sys_role_menu` VALUES ('7', '32');
INSERT INTO `sys_role_menu` VALUES ('7', '33');
INSERT INTO `sys_role_menu` VALUES ('7', '34');
INSERT INTO `sys_role_menu` VALUES ('7', '35');
INSERT INTO `sys_role_menu` VALUES ('7', '36');
INSERT INTO `sys_role_menu` VALUES ('7', '37');
INSERT INTO `sys_role_menu` VALUES ('7', '38');
INSERT INTO `sys_role_menu` VALUES ('7', '39');
INSERT INTO `sys_role_menu` VALUES ('7', '40');
INSERT INTO `sys_role_menu` VALUES ('7', '41');
INSERT INTO `sys_role_menu` VALUES ('7', '43');
INSERT INTO `sys_role_menu` VALUES ('7', '44');
INSERT INTO `sys_role_menu` VALUES ('7', '45');
INSERT INTO `sys_role_menu` VALUES ('7', '46');
INSERT INTO `sys_role_menu` VALUES ('7', '47');
INSERT INTO `sys_role_menu` VALUES ('7', '48');
INSERT INTO `sys_role_menu` VALUES ('7', '49');
INSERT INTO `sys_role_menu` VALUES ('7', '50');
INSERT INTO `sys_role_menu` VALUES ('7', '51');
INSERT INTO `sys_role_menu` VALUES ('7', '53');
INSERT INTO `sys_role_menu` VALUES ('7', '54');
INSERT INTO `sys_role_menu` VALUES ('7', '55');
INSERT INTO `sys_role_menu` VALUES ('7', '56');
INSERT INTO `sys_role_menu` VALUES ('7', '57');
INSERT INTO `sys_role_menu` VALUES ('7', '59');
INSERT INTO `sys_role_menu` VALUES ('7', '61');

-- ----------------------------
-- Table structure for sys_serious_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_serious_info`;
CREATE TABLE `sys_serious_info` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '区域ID',
  `user_id` bigint(20) NOT NULL,
  `age` tinyint(1) NOT NULL DEFAULT '0' COMMENT '年龄是否>65(否：0， 是：1)',
  `vaccines` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否完成全程疫苗接种（0：否， 1：是）',
  `with_disease` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否合并较为严重慢性疾病（0：否，1：是）',
  `discomfort` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有呼吸频率增快、持续性高热、浑身肌肉疼痛等症状（0：否，1：是）',
  `is_infected` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否感染（0：否， 1：是）',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_serious_info
-- ----------------------------
INSERT INTO `sys_serious_info` VALUES ('1', '1', '0', '1', '1', '0', '1', '0', 'admin', '2023-02-20 05:59:27', 'admin', '2023-03-08 13:38:29', null);
INSERT INTO `sys_serious_info` VALUES ('2', '3', '0', '0', '0', '0', '0', '0', 'test1', '2023-02-19 09:44:57', null, null, null);
INSERT INTO `sys_serious_info` VALUES ('3', '5', '0', '1', '0', '0', '0', '0', 'test2', '2023-02-21 13:07:29', '', null, null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `college_id` bigint(20) DEFAULT NULL COMMENT '班级ID',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '100', 'admin', '什禾', 'sh@163.com', '15888888888', '1', '/profile/admin/3fe7d43b-1202-4504-a08b-00d238a23ca4.png', '$2a$10$FaclWN9axBbnOBYCpP8M1.ph9mLM6ldsDfkoUlWa1C8P6eXafV6GS', '0', '0', 'admin', '2022-11-05 14:21:18', 'admin', '2023-02-28 11:37:24', '管理员');
INSERT INTO `sys_user` VALUES ('2', '101', 'sh', '什禾', 'sh@qq.com', '15666666666', '1', '', '$2a$10$yR.H2qcEBcBkGV611D8jAexNTmRgv/XWlGCEdqdI6yzL2nNoDPKoO', '0', '0', 'admin', '2022-11-05 22:21:18', 'admin', '2023-03-08 15:46:00', '测试员');
INSERT INTO `sys_user` VALUES ('3', '103', 'test1', '测试用户1', 'test1@qq.com', '13688888888', '0', '', '$2a$10$WGqOWMwV3PuKUjyPKQgZ9uT3nm6cLrSkjnKMpEGFa5agPpK7DAsAG', '0', '0', 'sh', '2022-11-22 09:23:45', 'admin', '2023-02-27 14:06:25', null);
INSERT INTO `sys_user` VALUES ('4', '103', 'test2', '测试用户2', 'test2@qq.com', '13288888888', '0', '', '$2a$10$UJ5aSMyiODeYZM8VFyISn.7p9JrlBlxdFlz67ON9NwUARyGFCLURy', '0', '0', 'sh', '2022-11-22 01:28:48', 'admin', '2023-02-27 14:06:30', null);
INSERT INTO `sys_user` VALUES ('5', '101', 'teacher', '教师', 'teacher@qq.com', '17999999999', '1', '/profile/teacher/8b994098-a867-4a3d-995a-c13dd1916e15.png', '$2a$10$SA0SsZi.tgGw.2LXby1OTO9azeovCcywOVgxzqcDjBwfGGeIGs1Ga', '0', '0', 'admin', '2023-02-12 18:13:56', 'teacher', '2023-03-08 15:49:38', null);
INSERT INTO `sys_user` VALUES ('6', '103', 'student', '学生', 'student@qq.com', '16000000000', '0', '/profile/student/957eed5f-34c8-42c5-904b-63c0138e62c6.png', '$2a$10$Ix7EUZ8pMqiVv0I6AOL.oOQpU0Dcc/cejxlh4C6PyLCYyAgt3MfXu', '0', '0', 'admin', '2023-02-13 02:15:01', 'admin', '2023-02-27 14:06:41', null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');
INSERT INTO `sys_user_role` VALUES ('3', '5');
INSERT INTO `sys_user_role` VALUES ('4', '5');
INSERT INTO `sys_user_role` VALUES ('5', '7');
INSERT INTO `sys_user_role` VALUES ('6', '6');
INSERT INTO `sys_user_role` VALUES ('7', '6');

-- ----------------------------
-- Table structure for sys_vaccines
-- ----------------------------
DROP TABLE IF EXISTS `sys_vaccines`;
CREATE TABLE `sys_vaccines` (
  `vaccines_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '疫苗ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL,
  `frequency` varchar(10) NOT NULL COMMENT '接种针次',
  `company` varchar(50) NOT NULL COMMENT '接种单位',
  `vaccines_brand` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '疫苗厂家品牌',
  `location` varchar(100) NOT NULL COMMENT '接种地点',
  `time` datetime NOT NULL COMMENT '接种时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`vaccines_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_vaccines
-- ----------------------------
INSERT INTO `sys_vaccines` VALUES ('1', '1', 'admin', '第一针', '广东防疫医院', '科兴', '广东防疫大学', '2023-02-14 18:01:54', '0', 'admin', '2023-02-14 18:02:02', '', null, null);
INSERT INTO `sys_vaccines` VALUES ('2', '1', 'admin', '第二针', '广东防疫医院', '科兴', '广东防疫大学', '2023-02-14 18:02:47', '0', 'admin', '2023-02-14 18:03:06', '', null, null);
INSERT INTO `sys_vaccines` VALUES ('3', '1', 'admin', '第三针', '广东防疫医院', '科兴', '广东防疫大学', '2023-02-14 18:03:43', '0', 'admin', '2023-02-14 18:03:52', '', null, null);
INSERT INTO `sys_vaccines` VALUES ('4', '2', 'sh', '第一针', '广东防疫医院', '生物', '广东防疫大学', '2023-02-27 17:22:21', '0', 'sh', '2023-02-27 17:22:29', '', null, null);
INSERT INTO `sys_vaccines` VALUES ('5', '2', 'sh', '第二针', '广东防疫医院', '生物', '广东防疫大学', '2023-02-27 17:22:57', '0', 'sh', '2023-02-27 17:23:02', '', null, null);
INSERT INTO `sys_vaccines` VALUES ('6', '3', 'test1', '第一针', '广东防疫医院', '生物', '广东防疫大学', '2023-02-27 17:23:52', '0', 'test1', '2023-02-27 17:23:57', '', null, null);
INSERT INTO `sys_vaccines` VALUES ('7', '2', 'sh', '第三针', '广东防疫医院', '生物', '广东防疫大学', '2023-02-27 17:50:47', '0', 'sh', '2023-02-27 17:50:52', '', null, null);
INSERT INTO `sys_vaccines` VALUES ('8', '4', 'test2', '第一针', '广东防疫医院', '科兴', '广东防疫大学', '2023-02-27 17:51:45', '0', 'test2', '2023-02-27 17:51:56', '', null, null);
INSERT INTO `sys_vaccines` VALUES ('9', '5', 'teacher', '第一针', '广东防疫医院', '科兴', '广东防疫大学', '2023-02-27 17:52:35', '0', 'teacher', '2023-02-27 17:52:41', '', null, null);
INSERT INTO `sys_vaccines` VALUES ('10', '6', 'student', '第一针', '广东防疫医院', '生物', '广东防疫大学', '2023-02-27 17:53:18', '0', 'student', '2023-02-27 17:53:25', '', null, null);
