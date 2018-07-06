/*
 Navicat MySQL Data Transfer

 Source Server         : shixun
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : willsea

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 06/07/2018 17:12:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for w_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `w_blacklist`;
CREATE TABLE `w_blacklist`  (
  `black_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `source_uid` int(10) UNSIGNED NULL DEFAULT 0,
  `target_uid` int(10) UNSIGNED NULL DEFAULT 0,
  PRIMARY KEY (`black_id`) USING BTREE,
  INDEX `source_uid`(`source_uid`) USING BTREE,
  INDEX `target_uid`(`target_uid`) USING BTREE,
  CONSTRAINT `w_blacklist_ibfk_1` FOREIGN KEY (`source_uid`) REFERENCES `w_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `w_blacklist_ibfk_2` FOREIGN KEY (`target_uid`) REFERENCES `w_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of w_blacklist
-- ----------------------------
INSERT INTO `w_blacklist` VALUES (2, 3, 4);

-- ----------------------------
-- Table structure for w_bottle
-- ----------------------------
DROP TABLE IF EXISTS `w_bottle`;
CREATE TABLE `w_bottle`  (
  `bid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `aid` int(10) UNSIGNED NULL DEFAULT 0,
  `btime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `title` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'undefined',
  `private` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'false',
  `btext` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'NULL',
  `baudio` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'NULL',
  `bvideo` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'NULL',
  PRIMARY KEY (`bid`) USING BTREE,
  INDEX `aid`(`aid`) USING BTREE,
  CONSTRAINT `w_bottle_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `w_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of w_bottle
-- ----------------------------
INSERT INTO `w_bottle` VALUES (2, 2, '20180625', 'leave naught behind', 'false', 'helloworld', 'NULL', 'NULL');
INSERT INTO `w_bottle` VALUES (3, 3, '20171213', 'deep deep woe', 'false', 'helloworld', 'NULL', 'NULL');
INSERT INTO `w_bottle` VALUES (4, 4, '20180612', 'a empty home by the sea', 'false', 'helloworld', 'NULL', 'NULL');
INSERT INTO `w_bottle` VALUES (5, 5, '20181122', 'one soul wild awake', 'false', 'helloworld', 'NULL', 'NULL');
INSERT INTO `w_bottle` VALUES (7, 7, '20181211', 'Is moonlight without dark', 'false', 'helloworld', 'NULL', 'NULL');
INSERT INTO `w_bottle` VALUES (8, 8, '20181211', 'seeketh equilibrium', 'false', 'helloworld', 'NULL', 'NULL');
INSERT INTO `w_bottle` VALUES (9, 9, '20181211', 'your worry part,', 'false', 'helloworld', 'NULL', 'NULL');
INSERT INTO `w_bottle` VALUES (18, 1, '2018-07-06 16:28:49', '测试', 'true', '视频测试', 'NULL', 'NULL');

-- ----------------------------
-- Table structure for w_comment
-- ----------------------------
DROP TABLE IF EXISTS `w_comment`;
CREATE TABLE `w_comment`  (
  `cid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `aid` int(10) UNSIGNED NULL DEFAULT 0,
  `bid` int(10) UNSIGNED NULL DEFAULT 0,
  `ctime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE,
  INDEX `aid`(`aid`) USING BTREE,
  INDEX `bid`(`bid`) USING BTREE,
  CONSTRAINT `w_comment_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `w_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `w_comment_ibfk_2` FOREIGN KEY (`bid`) REFERENCES `w_bottle` (`bid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of w_comment
-- ----------------------------
INSERT INTO `w_comment` VALUES (2, 2, 2, '20180626', 'Forsaken, beaten, tried, on her knees');
INSERT INTO `w_comment` VALUES (3, 3, 3, '20180626', 'Forsaken, beaten, tried, on her knees');
INSERT INTO `w_comment` VALUES (4, 4, 4, '20180626', 'A prayer passes from her lips');
INSERT INTO `w_comment` VALUES (5, 5, 5, '20180626', 'nto her soul the Goddess whispers:');
INSERT INTO `w_comment` VALUES (7, 7, 7, '20180626', 'One last step only leaving');
INSERT INTO `w_comment` VALUES (8, 8, 8, '20180626', 'The sigh of the shifting sea');
INSERT INTO `w_comment` VALUES (9, 9, 9, '20180626', 'So still this broken melody s');
INSERT INTO `w_comment` VALUES (18, 1, 4, '2018-07-05 19:18:21', 'add');
INSERT INTO `w_comment` VALUES (19, 1, 4, '2018-07-05 19:18:31', 'one comment');
INSERT INTO `w_comment` VALUES (20, 1, 4, '2018-07-05 19:18:41', 'shw');
INSERT INTO `w_comment` VALUES (27, 1, 2, '2018-07-05 22:16:12', '那是无名，成不了薪。且被诅咒的不死人。也正因如此，灰烬才如此渴望余火吧。');
INSERT INTO `w_comment` VALUES (28, 1, 2, '2018-07-05 22:19:10', '而无火的余灰将纷至沓来');
INSERT INTO `w_comment` VALUES (30, 1, 2, '2018-07-05 22:21:23', '古楼的钟声响起，进而唤醒沉睡的薪王们。');
INSERT INTO `w_comment` VALUES (31, 1, 2, '2018-07-05 22:21:56', '火已渐熄，然位不见王影.');
INSERT INTO `w_comment` VALUES (32, 1, 7, '2018-07-06 09:07:34', '日常+1');
INSERT INTO `w_comment` VALUES (33, 1, 5, '2018-07-06 09:52:49', 'lay down your arm and let the evil inside');
INSERT INTO `w_comment` VALUES (34, 1, 18, '2018-07-06 16:29:20', 'rock and roll');
INSERT INTO `w_comment` VALUES (35, 1, 18, '2018-07-06 16:29:55', 'just dance alone to the beats of your heart');
INSERT INTO `w_comment` VALUES (36, 1, 4, '2018-07-06 16:35:29', '嚯嚯，你开始了？');
INSERT INTO `w_comment` VALUES (37, 1, 18, '2018-07-06 17:09:37', 'show me the flower');
INSERT INTO `w_comment` VALUES (38, 1, 18, '2018-07-06 17:09:46', '什么情况');

-- ----------------------------
-- Table structure for w_favorite
-- ----------------------------
DROP TABLE IF EXISTS `w_favorite`;
CREATE TABLE `w_favorite`  (
  `favorite_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `source_uid` int(10) UNSIGNED NULL DEFAULT 0,
  `target_uid` int(10) UNSIGNED NULL DEFAULT 0,
  PRIMARY KEY (`favorite_id`) USING BTREE,
  INDEX `source_uid`(`source_uid`) USING BTREE,
  INDEX `target_uid`(`target_uid`) USING BTREE,
  CONSTRAINT `w_favorite_ibfk_1` FOREIGN KEY (`source_uid`) REFERENCES `w_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `w_favorite_ibfk_2` FOREIGN KEY (`target_uid`) REFERENCES `w_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of w_favorite
-- ----------------------------
INSERT INTO `w_favorite` VALUES (23, 1, 7);
INSERT INTO `w_favorite` VALUES (24, 1, 5);
INSERT INTO `w_favorite` VALUES (25, 1, 2);
INSERT INTO `w_favorite` VALUES (26, 1, 3);
INSERT INTO `w_favorite` VALUES (27, 1, 8);

-- ----------------------------
-- Table structure for w_user
-- ----------------------------
DROP TABLE IF EXISTS `w_user`;
CREATE TABLE `w_user`  (
  `uid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `utype` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'user',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `upassword` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uemail` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `forbidden` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'false',
  `createTime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` int(2) NULL DEFAULT NULL,
  `telephone` bigint(32) NULL DEFAULT NULL,
  `location` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `headImageUrl` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'NULL',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `uname`(`username`) USING BTREE,
  UNIQUE INDEX `uemail`(`uemail`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of w_user
-- ----------------------------
INSERT INTO `w_user` VALUES (1, 'user', 'LudexGundyr', 'LudexGundyr', '3366@hust.com', 'true', '20180620', 1, 13012131415, 'wuhan.China', '20131415', '/user/headImage/king.jpg');
INSERT INTO `w_user` VALUES (2, 'admin', 'Farron', 'FarronUndeadLegion', '1122@hust,com', 'true', '20180621', 0, 13212121314, 'whang.China', '20121112', '/user/headImage/head.jpg');
INSERT INTO `w_user` VALUES (3, 'user', 'Yhorm', 'YhormTheGiant', '1133@hust,com', 'true', '20180622', 1, 13201021314, 'whang.China', '20121112', '/user/headImage/head.jpg');
INSERT INTO `w_user` VALUES (4, 'user', 'Aldrich', 'DevourerOfGods', '1123@hust,com', 'true', '20180621', 0, 13114151314, 'whang.China', '20121112', '/user/headImage/head.jpg');
INSERT INTO `w_user` VALUES (5, 'user', 'Ozroes', 'TheCondemnedKing', '3123@hust,com', 'true', '20180621', 1, 13212121314, 'whang.China', '20121112', '/user/headImage/head.jpg');
INSERT INTO `w_user` VALUES (6, 'user', 'Lorian', 'ElderPrince', '31343@hust,com', 'true', '20180621', 0, 13212121314, 'whang.China', '20121112', '/user/headImage/head.jpg');
INSERT INTO `w_user` VALUES (7, 'user', 'SoulO', 'LordOfCinder', 'lordofcinder@hust,com', 'true', '20180622', 1, 13212121314, 'whang.China', '20121112', '/user/headImage/head.jpg');
INSERT INTO `w_user` VALUES (8, 'user', 'NameL', 'LordOfCinder', 'nameLessKing@hust.com', 'false', '20180623', 0, 13212121314, 'whang.China', '20121112', '/user/headImage/head.jpg');
INSERT INTO `w_user` VALUES (9, 'admin', 'france', 'FLegionwd', '10304@hust,com', 'true', '20180625', 0, 13312121314, 'shenzhen.China', '20141112', '/user/headImage/code.png');
INSERT INTO `w_user` VALUES (10, 'user', 'american', 'FLegionwd', '301234@hust,com', 'true', '20180627', 1, 13012131918, 'shenzhen.China', '20131112', '/user/headImage/cing.png');
INSERT INTO `w_user` VALUES (21, NULL, 'admin', '123456', '786047498@qq.com', NULL, '1530582846033', 1, 13006366818, '武汉', '19971018', NULL);
INSERT INTO `w_user` VALUES (22, NULL, 'suifengsuixing', '123456789', '7860@qq.com', NULL, '1530583074293', 1, 13010101010, '武汉', '19971018', NULL);
INSERT INTO `w_user` VALUES (24, NULL, 'yisui', '123456789', '7860444@qq.com', NULL, '1530583236512', 1, 13331313232, '武汉', '19971018', NULL);
INSERT INTO `w_user` VALUES (25, NULL, 'corry', '123456789', '67399230@qq.com', NULL, '1530583472195', 1, 13213141413, '北京', '19931018', NULL);
INSERT INTO `w_user` VALUES (26, '', 'ebrietas', 'whatthefoxsay.', '932435644@qq.com', NULL, '1530759649319', 1, 15927593795, 'wuhan', '19971014', NULL);

SET FOREIGN_KEY_CHECKS = 1;
