/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2020-05-25 16:43:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('3', '哈姆雷特', 'author', '2020-05-25 15:53:54');
INSERT INTO `book` VALUES ('9', '哈姆雷特', 'author3', '2020-05-25 15:58:24');
INSERT INTO `book` VALUES ('10', '哈姆雷特', 'author3', '2020-05-25 15:58:33');
INSERT INTO `book` VALUES ('11', '哈姆雷特', 'author3', '2020-05-25 15:58:34');
INSERT INTO `book` VALUES ('12', '哈姆雷特', 'author3', '2020-05-25 16:01:13');
