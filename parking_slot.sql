/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : localhost:3306
 Source Schema         : parking_slot

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 06/04/2024 18:45:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for denunciation
-- ----------------------------
DROP TABLE IF EXISTS `denunciation`;
CREATE TABLE `denunciation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '举报内容',
  `slot_id` int NOT NULL COMMENT '停车场编号',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '微信号',
  `status` int NULL DEFAULT 0 COMMENT '处理状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_denunciation_slotId`(`slot_id` ASC) USING BTREE,
  CONSTRAINT `fk_denunciation_slotId` FOREIGN KEY (`slot_id`) REFERENCES `park_slot` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '举报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of denunciation
-- ----------------------------
INSERT INTO `denunciation` VALUES (2, '太贵了', 5, 'orIbL6woaiRGoD2PQX6rGg3VPhrY', 1);

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '反馈内容',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '微信号',
  `status` int NULL DEFAULT 0 COMMENT '处理状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '反馈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (3, '非常好', 'orIbL6woaiRGoD2PQX6rGg3VPhrY', 0);

-- ----------------------------
-- Table structure for messages
-- ----------------------------
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '微信号',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `status` int NULL DEFAULT 0 COMMENT '已读状态',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of messages
-- ----------------------------
INSERT INTO `messages` VALUES (3, 'oX2YX4whRalWQ5BGmAoQmHB9zIZQ', '123', 1, '2024-03-26 15:09:38');
INSERT INTO `messages` VALUES (4, 'oX2YX4whRalWQ5BGmAoQmHB9zIZQ', '滚', 1, '2024-03-26 15:19:58');
INSERT INTO `messages` VALUES (6, 'orIbL6woaiRGoD2PQX6rGg3VPhrY', '处理', 1, '2024-03-28 18:34:25');

-- ----------------------------
-- Table structure for park_slot
-- ----------------------------
DROP TABLE IF EXISTS `park_slot`;
CREATE TABLE `park_slot`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lng` double NOT NULL COMMENT '经度',
  `lat` double NOT NULL COMMENT '纬度',
  `slot_number` int NOT NULL COMMENT '车位总量',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地址',
  `use_number` int NOT NULL DEFAULT 0 COMMENT '占用数量',
  `price` double NOT NULL COMMENT '价格',
  `extra_price` double NULL DEFAULT 1 COMMENT '超时价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '停车场表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of park_slot
-- ----------------------------
INSERT INTO `park_slot` VALUES (4, 106.26667, 38.46667, 10, '南宁民歌湖', 1, 10, 15);
INSERT INTO `park_slot` VALUES (5, 108.372387, 22.801056, 15, '广西壮族自治区南宁市青秀区津头街道广源国际社区11栋', 0, 6, 12);
INSERT INTO `park_slot` VALUES (6, 108.374142, 22.809926, 13, '广西壮族自治区南宁市青秀区津头街道金浦路23号民歌湖国际音乐小镇', 0, 10, 12);
INSERT INTO `park_slot` VALUES (7, 108.374245, 22.809693, 10, '广西壮族自治区南宁市青秀区津头街道民歌广场民歌湖国际音乐小镇', 0, 5, 8);
INSERT INTO `park_slot` VALUES (8, 108.374152, 22.808721, 13, '广西壮族自治区南宁市青秀区津头街道民歌广场民歌湖国际音乐小镇', 0, 10, 12);

-- ----------------------------
-- Table structure for records
-- ----------------------------
DROP TABLE IF EXISTS `records`;
CREATE TABLE `records`  (
  `id` bigint NOT NULL COMMENT '编号',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '微信号',
  `time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '预约时间',
  `how_long` int NOT NULL COMMENT '预计时长',
  `status` int NOT NULL DEFAULT 0 COMMENT '停车状态',
  `slot_id` int NOT NULL COMMENT '停车场编号',
  `end_time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '结束时间',
  `price` double NOT NULL COMMENT '价格',
  `car_slot_id` int NOT NULL COMMENT '车位',
  `start_time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开始时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_records_slotId`(`slot_id` ASC) USING BTREE,
  CONSTRAINT `fk_records_slotId` FOREIGN KEY (`slot_id`) REFERENCES `park_slot` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '预约记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of records
-- ----------------------------
INSERT INTO `records` VALUES (1773289209965076482, 'orIbL6woaiRGoD2PQX6rGg3VPhrY', '2024-03-30 18:01:02', 2, 3, 5, '2024-03-28 18:01:13', 15, 1, '2024-03-28 18:01:11');
INSERT INTO `records` VALUES (1773289446532210690, 'orIbL6woaiRGoD2PQX6rGg3VPhrY', '2024-04-05 18:01:32', 2, 3, 4, '2024-03-28 18:37:42', 15, 2, '2024-03-28 18:37:30');
INSERT INTO `records` VALUES (1773298153496342530, 'orIbL6woaiRGoD2PQX6rGg3VPhrY', '2024-04-03 18:36:27', 12, 2, 4, '2024-03-29 12:10:16', 84, 3, '2024-03-29 12:10:15');
INSERT INTO `records` VALUES (1773566845064843265, 'orIbL6woaiRGoD2PQX6rGg3VPhrY', '2024-04-03 12:24:17', 1, 2, 4, '2024-03-29 12:26:18', 7.5, 2, '2024-03-29 12:26:15');

-- ----------------------------
-- Table structure for tips
-- ----------------------------
DROP TABLE IF EXISTS `tips`;
CREATE TABLE `tips`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tips
-- ----------------------------
INSERT INTO `tips` VALUES (4, '收费说明', '下面是一个简单的停车场收费说明示例：\n\n---\n\n### 停车场收费说明\n\n欢迎来到我们的停车场！以下是我们的收费标准和说明：\n\n#### 收费标准\n\n1. **小时收费**：\n   - 每小时收费：$2\n   - 最大收费上限：$20（每天）\n\n2. **特殊车辆**：\n   - 残疾人车辆：免费停车\n   - 电动车：半价优惠\n\n3. **长期停车**：\n   - 一周停车：$80\n   - 一个月停车：$300\n\n#### 付款方式\n\n- 我们接受现金、信用卡和移动支付。\n- 请在停车后前往收费亭结账。\n\n#### 注意事项\n\n- 请妥善保管好您的停车券，以免丢失。\n- 超过预定停车时间将按照额外收费标准收费。\n\n#### 联系方式\n\n如果您有任何疑问或需要帮助，请联系我们的停车场管理员：\n\n- 电话：123-456-7890\n- 电子邮件：parking@example.com\n\n感谢您选择我们的停车场！祝您停车愉快！\n\n---\n\n你可以根据实际情况调整收费标准、付款方式和注意事项等内容。如果你有任何特定要求或需要进一步的帮助，请随时告诉我。', '2024-03-28 16:55:20');
INSERT INTO `tips` VALUES (5, '规则', '### 停车场公告\n\n尊敬的停车场用户，\n\n为了提供更好的停车体验和管理停车场秩序，我们制定了以下规定，请您遵守：\n\n1. **停车场使用时间**：\n   - 停车场开放时间为每天早上 6 点至晚上 10 点。\n   - 请在营业时间内停放车辆，超时停车将收取额外费用。\n\n2. **安全提示**：\n   - 请锁好车门，不要在车内存放贵重物品。\n   - 禁止在停车场内吸烟或施放火种。\n\n3. **环境保护**：\n   - 请垃圾分类，保持停车场环境整洁。\n   - 禁止乱丢垃圾，违者将被处以罚款。\n\n4. **特殊车辆**：\n   - 残疾人车辆优先停放残疾人专用车位。\n   - 电动车请使用指定充电桩停放。\n\n5. **停车费用**：\n   - 请在停车后及时缴纳停车费用，逾期将产生额外费用。\n   - 长期停车用户可享受优惠价格，详情请咨询停车场管理员。\n\n6. **联系方式**：\n   - 如有任何问题或紧急情况，请拨打停车场管理员电话：123-456-7890。\n\n感谢您的配合与支持，让我们共同维护一个安全、有序的停车环境。祝您停车愉快！\n\n停车场管理团队 敬上', '2024-03-28 17:06:21');
INSERT INTO `tips` VALUES (7, '测试', '测试', '2024-03-28 18:33:14');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '微信号',
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '无' COMMENT '手机号',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '无' COMMENT '名字',
  `car_card` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '无' COMMENT '车牌',
  `car_kind` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '无' COMMENT '车名',
  `money` double NOT NULL DEFAULT 0 COMMENT '余额',
  `status` int NOT NULL DEFAULT 0 COMMENT '身份',
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('orIbL6woaiRGoD2PQX6rGg3VPhrY', '12345678912', '阿达', '鲁 W2E', '宝马', 1060, 1);
INSERT INTO `users` VALUES ('oX2YX4whRalWQ5BGmAoQmHB9zIZQ', '15050239085', '武术安', '鲁B 1E4C5', '宝马', 30, 1);

SET FOREIGN_KEY_CHECKS = 1;
