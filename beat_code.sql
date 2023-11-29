/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50744
Source Host           : 127.0.0.1:3306
Source Database       : beat_code

Target Server Type    : MYSQL
Target Server Version : 50744
File Encoding         : 65001

Date: 2023-11-28 10:02:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for code_room
-- ----------------------------
DROP TABLE IF EXISTS `code_room`;
CREATE TABLE `code_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_code` char(8) DEFAULT NULL,
  `room_owner_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for difficulty
-- ----------------------------
DROP TABLE IF EXISTS `difficulty`;
CREATE TABLE `difficulty` (
  `id` int(11) NOT NULL,
  `level` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for questions
-- ----------------------------
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `title_slug` varchar(75) NOT NULL,
  `num_parameters` int(11) NOT NULL,
  `main_def` varchar(200) NOT NULL,
  `difficulty` int(11) NOT NULL,
  `topic_slugs` varchar(45) NOT NULL,
  `hint` varchar(500) DEFAULT NULL,
  `constraints` varchar(1000) NOT NULL,
  `examples_str_format` varchar(2000) NOT NULL,
  `output_type` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `difficulty` (`difficulty`),
  KEY `output_type` (`output_type`),
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`difficulty`) REFERENCES `difficulty` (`id`),
  CONSTRAINT `questions_ibfk_2` FOREIGN KEY (`output_type`) REFERENCES `variable_type` (`var_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for question_test_cases
-- ----------------------------
DROP TABLE IF EXISTS `question_test_cases`;
CREATE TABLE `question_test_cases` (
  `question_id` int(11) NOT NULL,
  `test_case_id` int(11) NOT NULL,
  KEY `question_id` (`question_id`),
  KEY `test_case_id` (`test_case_id`),
  CONSTRAINT `question_test_cases_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `question_test_cases_ibfk_2` FOREIGN KEY (`test_case_id`) REFERENCES `test_cases` (`test_case_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for question_topics
-- ----------------------------
DROP TABLE IF EXISTS `question_topics`;
CREATE TABLE `question_topics` (
  `question_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  KEY `question_id` (`question_id`),
  KEY `topic_id` (`topic_id`),
  CONSTRAINT `question_topics_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `question_topics_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for test_cases
-- ----------------------------
DROP TABLE IF EXISTS `test_cases`;
CREATE TABLE `test_cases` (
  `test_case_id` int(11) NOT NULL,
  `input` varchar(500) NOT NULL,
  `param_names_str` varchar(500) NOT NULL,
  `output` varchar(45) NOT NULL,
  `output_type` int(11) NOT NULL,
  PRIMARY KEY (`test_case_id`),
  KEY `output_type` (`output_type`),
  CONSTRAINT `test_cases_ibfk_1` FOREIGN KEY (`output_type`) REFERENCES `variable_type` (`var_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for topics
-- ----------------------------
DROP TABLE IF EXISTS `topics`;
CREATE TABLE `topics` (
  `topic_id` int(11) NOT NULL,
  `topic_name` varchar(50) NOT NULL,
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(15) DEFAULT NULL,
  `password` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for variable_type
-- ----------------------------
DROP TABLE IF EXISTS `variable_type`;
CREATE TABLE `variable_type` (
  `var_type_id` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`var_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
