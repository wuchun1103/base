/*
 Navicat Premium Data Transfer

 Source Server         : MY
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : 192.168.2.64:3306
 Source Schema         : base_test

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 17/05/2021 16:35:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `type` int(0) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(0) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(0) NULL DEFAULT 1 COMMENT '是否删除  1：未删除  2：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, NULL, NULL, 0, 'fa fa-cog', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, '/sys/user', NULL, 1, 'fa fa-users', 1, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4, '角色管理', 1, '/sys/role', NULL, 1, 'fa fa-address-book', 4, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (5, '菜单管理', 1, '/sys/menu', NULL, 1, 'fa fa-tasks', 3, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (9, '查看', 2, NULL, 'sys:user:view', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (10, '新增', 2, NULL, 'sys:user:add', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (11, '修改', 2, NULL, 'sys:user:edit', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (12, '删除', 2, NULL, 'sys:user:delete', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (17, '查看', 4, NULL, 'sys:role:view', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (18, '新增', 4, NULL, 'sys:role:add', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (19, '修改', 4, NULL, 'sys:role:edit', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (20, '删除', 4, NULL, 'sys:role:delete', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (21, '查看', 5, NULL, 'sys:menu:view', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (22, '新增', 5, NULL, 'sys:menu:add', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (23, '修改', 5, NULL, 'sys:menu:edit', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (24, '删除', 5, NULL, 'sys:menu:delete', 2, NULL, 0, NULL, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(0) NULL DEFAULT 1 COMMENT '是否删除  1：未删除  2：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', '超级管理员', 'admin', '2018-08-14 11:11:11', 'admin', '2018-08-14 11:11:11', 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` int(0) NULL DEFAULT NULL COMMENT '菜单ID',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (2, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (4, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (5, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (9, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (10, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (11, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (12, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (17, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (18, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (19, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (20, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (21, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (22, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (23, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (24, 1, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `passWord` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `salt` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `userName` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `gender` tinyint(0) NULL DEFAULT NULL COMMENT '1=男；2=女',
  `headUrl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `userEmail` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `userPhone` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `birthday` datetime(0) NULL DEFAULT NULL,
  `jobName` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `remark` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(0) NULL DEFAULT 1 COMMENT '是否被删除 1-未删除，2-已删除',
  `use_status` int(0) NULL DEFAULT 1 COMMENT '是否被禁用 1-未禁用，2-已禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'zay123', NULL, '管理员', 2, NULL, NULL, '18761863765', '2009-06-10 17:47:23', '', NULL, '', '2021-04-12 10:50:30', NULL, '2021-04-21 15:12:10', 2, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Function structure for underlineToCamel
-- ----------------------------
DROP FUNCTION IF EXISTS `underlineToCamel`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `underlineToCamel`(paramString VARCHAR(200)) RETURNS varchar(200) CHARSET utf8
    DETERMINISTIC
begin
		set paramString = replace(paramString, '_a', 'A');
    set paramString = replace(paramString, '_b', 'B');
    set paramString = replace(paramString, '_c', 'C');
    set paramString = replace(paramString, '_d', 'D');
    set paramString = replace(paramString, '_e', 'E');
    set paramString = replace(paramString, '_f', 'F');
    set paramString = replace(paramString, '_g', 'G');
    set paramString = replace(paramString, '_h', 'H');
    set paramString = replace(paramString, '_i', 'I');
    set paramString = replace(paramString, '_j', 'J');
    set paramString = replace(paramString, '_k', 'K');
    set paramString = replace(paramString, '_l', 'L');
    set paramString = replace(paramString, '_m', 'M');
    set paramString = replace(paramString, '_n', 'N');
    set paramString = replace(paramString, '_o', 'O');
    set paramString = replace(paramString, '_p', 'P');
    set paramString = replace(paramString, '_q', 'Q');
    set paramString = replace(paramString, '_r', 'R');
    set paramString = replace(paramString, '_s', 'S');
    set paramString = replace(paramString, '_t', 'T');
    set paramString = replace(paramString, '_u', 'U');
    set paramString = replace(paramString, '_v', 'V');
    set paramString = replace(paramString, '_w', 'W');
    set paramString = replace(paramString, '_x', 'X');
    set paramString = replace(paramString, '_y', 'Y');
    set paramString = replace(paramString, '_z', 'Z');
    set paramString = replace(paramString, '_', '');
    RETURN paramString;
	end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
