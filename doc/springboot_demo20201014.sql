/*
Navicat MySQL Data Transfer

Source Server         : localhost_mysql8
Source Server Version : 80015
Source Host           : 127.0.0.1:3307
Source Database       : springboot_demo

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2020-10-14 08:31:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for contract_template
-- ----------------------------
DROP TABLE IF EXISTS `contract_template`;
CREATE TABLE `contract_template` (
  `template_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板id',
  `template_name` varchar(200) DEFAULT NULL COMMENT '模板名称',
  `template_status` tinyint(4) DEFAULT NULL COMMENT '模板状态：0.禁用，1.使用',
  `template_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '模板内容',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道id',
  `type` smallint(6) DEFAULT NULL COMMENT '类型',
  `version` varchar(100) DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否删除  1：已删除  0：正常',
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同模板表';

-- ----------------------------
-- Records of contract_template
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '标签名',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据值',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父级编号',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`name`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件名',
  `file_ext` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件扩展名',
  `file_full` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件全名',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT 'URL地址',
  `server_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件服务器地址',
  `group_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '文件服务器路径',
  `belong_id` bigint(20) DEFAULT NULL COMMENT '文件属于',
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件附属类型',
  `file_tag` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件标签',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(512) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '0 未删除 1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `file_belong_id` (`belong_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='文件表';

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_plan_remind
-- ----------------------------
DROP TABLE IF EXISTS `sys_plan_remind`;
CREATE TABLE `sys_plan_remind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plan_type` tinyint(4) DEFAULT NULL COMMENT '计划类型：0：单次执行，1：每日执行，2：周期执行，3：星期执行，4：周期星期执行',
  `execute_status` tinyint(4) DEFAULT NULL COMMENT '执行状态：0：关闭，1：启动',
  `topic` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主题',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
  `remind_type` tinyint(4) DEFAULT NULL COMMENT '提醒类型：0：邮件',
  `remind_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提醒值',
  `finish_flag` tinyint(4) DEFAULT NULL COMMENT '完成标识：0：未完成，1：完成',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `execute_date` date DEFAULT NULL COMMENT '执行日期',
  `execute_time` time DEFAULT NULL COMMENT '执行时间',
  `cycle_number` int(11) DEFAULT NULL COMMENT '周期个数',
  `cycle_unit` tinyint(4) DEFAULT NULL COMMENT '周期单位，0：秒，1：分，2：时，3：天，4：月，5：年',
  `recent_execute_date` datetime DEFAULT NULL COMMENT '最近一次执行时间',
  `monday_flag` tinyint(4) DEFAULT NULL COMMENT ' 周一标识：0，否，1：是',
  `tuesday_flag` tinyint(4) DEFAULT NULL COMMENT ' 周二标识：0，否，2：是',
  `wednesday_flag` tinyint(4) DEFAULT NULL COMMENT ' 周三标识：0，否，3：是',
  `thursday_flag` tinyint(4) DEFAULT NULL COMMENT ' 周四标识：0，否，4：是',
  `friday_flag` tinyint(4) DEFAULT NULL COMMENT ' 周五标识：0，否，4：是',
  `saturday_flag` tinyint(4) DEFAULT NULL COMMENT ' 周六标识：0，否，6：是',
  `sunday_flag` tinyint(4) DEFAULT NULL COMMENT ' 周日标识：0，否，7：是',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否删除  1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='计划提醒表';

-- ----------------------------
-- Records of sys_plan_remind
-- ----------------------------
INSERT INTO `sys_plan_remind` VALUES ('12', '2', '1', '每日执行测试', '每日执行测试', '0', 'hanxinghua2015@sina.com', '0', '2020-07-16', null, null, '20:33:01', '1', '1', '2020-09-09 22:56:01', null, null, null, null, null, null, null, '1', '2020-07-06 20:31:12', '1', '2020-09-09 22:56:00', null, '0');
