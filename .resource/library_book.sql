/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2020-05-25 16:43:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for library_book
-- ----------------------------
DROP TABLE IF EXISTS `library_book`;
CREATE TABLE `library_book` (
  `book_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of library_book
-- ----------------------------
INSERT INTO `library_book` VALUES ('1', 'demoData', '1', '1', '2020-01-06 15:11:33', null);
INSERT INTO `library_book` VALUES ('2', 'demoDat2', '2', '2', '2020-01-06 15:11:34', null);
INSERT INTO `library_book` VALUES ('3', 'demoData', '1', '1', '2020-01-06 15:11:33', null);
INSERT INTO `library_book` VALUES ('4', 'demoDat2', '2', '2', '2020-01-06 15:11:34', null);
INSERT INTO `library_book` VALUES ('5', 'demoData', '1', '1', '2020-01-06 15:11:33', '2020-01-06 15:11:33');
INSERT INTO `library_book` VALUES ('6', 'demoDat2', '2', '2', '2020-01-06 15:11:34', '2020-01-06 15:11:34');
INSERT INTO `library_book` VALUES ('7', 'demoData', '1', '1', '2020-01-06 15:11:33', '2020-01-06 15:11:33');
INSERT INTO `library_book` VALUES ('8', 'demoDat2', '2', '2', '2020-01-06 15:11:34', '2020-01-06 15:11:34');
INSERT INTO `library_book` VALUES ('9', 'demoData', '1', '1', '2020-01-06 15:11:33', '2020-01-06 15:11:33');
INSERT INTO `library_book` VALUES ('10', 'demoDat2', '2', '2', '2020-01-06 15:11:34', '2020-01-06 15:11:34');
