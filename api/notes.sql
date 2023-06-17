-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: notes
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `article_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(50) NOT NULL COMMENT '文章标题',
  `user_id` bigint NOT NULL COMMENT '当前文章关联的用户 id',
  `type` int DEFAULT '1' COMMENT '0 待审核 1 我的作品 2 收藏 3 喜欢 4 点赞',
  `reading` bigint DEFAULT '0' COMMENT '阅读量',
  `common` bigint DEFAULT '0' COMMENT '评论量',
  `love` bigint DEFAULT '0' COMMENT '点赞量',
  `created_date` date DEFAULT NULL COMMENT '文章创建时间',
  `img` varchar(255) DEFAULT NULL COMMENT '文章图片',
  `heat` bigint DEFAULT '0' COMMENT '当前文章热度是否推荐到首页',
  `content` text NOT NULL COMMENT 'md 文档内容',
  `catalog_id` bigint NOT NULL COMMENT '分类 id',
  `status` int DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'fdsafdsa',1,1,0,0,0,'2023-02-09',NULL,0,'fsdafdsfsd',2,0),(2,'test',1,1,0,0,0,'2023-02-09',NULL,0,'test',2,0),(4,'test',1,1,0,0,0,'2023-02-09',NULL,0,'### 标题一\n\n哈哈哈\n\n### 标题二\n\n哈哈\n\n### 标题三\n\n好几家',2,0),(7,'哈哈哈',1,1,0,0,0,'2023-02-10',NULL,0,'# 哈哈哈\n### 1、Spring6 源码：\n```java\n> public void main(String args) {\n    int res = 0;\n    System.out.println(\"结果为：\" + res);\n}\n```\n\n### 2、MySql 从入门到入土\n```\n# 查询语句\nselect * from 表名\n\n# 修改语句\nupdate set 字段名=值... where 条件\n\n# 添加语句\ninsert into(字段名...) values (值)\n\n# 删除语句\ndelete from 表名 where 条件\n\n# 删除表结构\ntruncate table 表名\n\n```\n### 结束语\n欢迎使用此笔记`预览`，哈哈哈哈\n\n![Description](https://gw.alicdn.com/imgextra/i4/O1CN01ZaP2Or1ZzsLJJb4Wg_!!6000000003266-0-tps-800-450.jpg)',2,0),(8,'测试文章',1,1,0,0,0,'2023-02-10',NULL,0,'## 标题一\n哈哈哈\n## 标题二\n哈哈哈\n## 标题三\n#### 四级标题\n\n\n### 哈哈哈\n\n--- 哈哈哈\n\n\n\n',2,0),(11,'Java 从入门到入土',1,1,0,0,0,'2023-02-10','5e0j2b4d3c2b1a6f4d1a0j9i2b2b1a2b.png',0,'### 初始Java\n\n```java\n什么是Java？\n```\n### Java的数据类型？\n```java\n1、int\n2、double\n3、String\n4、boolean\n5、chat\n```\n### Java输入\n```java\nScanner sc = new Scanner(System.in);\nint num = sc.nextInt();\n```\n#### 完结。\n\n\n\n\n\n\n',2,0),(13,'个个都是',1,1,0,0,0,'2023-02-12','2b7g4d3c3c2b4d2b3c7g3c9i1a8h8h5e.png',0,'给大哥萨达',2,0),(14,'欢喜大哥',2,1,0,0,0,'2023-02-13',NULL,0,'### 标题一\n\n123\n\n### 标贴\n\n4156\n\n#### 哈哈哈',2,0),(15,'yyyyyyy',4,1,0,0,0,'2023-02-24','9i8h5e0j5e5e3c5e2b9i7g6f7g2b5e6f.png',0,'# 1\n![Description](http://localhost:9003/upload/temp/2b6f7g2b1a9i4d7g8h4d1a0j1a8h5e0j.png)\n# 2\n![Description](http://localhost:9003/upload/temp/8h7g8h9i8h6f6f1a6f4d3c9i3c8h9i0j.png)\n# 3\n![Description](http://localhost:9003/upload/temp/8h7g9i0j0j4d0j3c2b1a7g4d3c0j1a8h.png)\n# 4\n![Description](http://localhost:9003/upload/temp/7g1a5e4d9i8h8h4d4d0j3c6f0j4d2b1a.png)\n# 5\n![Description](http://localhost:9003/upload/temp/5e3c9i0j2b6f2b6f6f1a5e5e7g6f5e5e.png)\n\n\n\n\n',2,0),(16,'ttttt',4,0,0,0,0,'2023-02-24',NULL,0,'tttttt',2,0),(17,'ggggg',4,1,0,0,0,'2023-02-24','1a8h6f5e6f7g0j2b8h0j1a8h5e2b3c9i.png',0,'ggggg',2,0),(18,'bbbbb',4,1,0,0,0,'2023-02-24','6f4d9i9i6f6f5e9i2b0j0j5e2b4d7g7g.png',0,'bbbbbb\n![Description](http://localhost:9003/upload/temp/8h7g0j4d9i7g3c2b7g7g9i4d6f2b7g9i.png)',2,0);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_link`
--

DROP TABLE IF EXISTS `article_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_link` (
  `al_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint NOT NULL COMMENT '文章 id',
  `type` int DEFAULT '1' COMMENT '1 收藏 2 喜欢 3 点赞',
  `status` int DEFAULT '0' COMMENT '0 未删除 1 已删除',
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`al_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_link`
--

LOCK TABLES `article_link` WRITE;
/*!40000 ALTER TABLE `article_link` DISABLE KEYS */;
INSERT INTO `article_link` VALUES (36,7,5,1,1),(38,7,4,1,1),(39,7,3,1,2),(40,1,5,1,2);
/*!40000 ALTER TABLE `article_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalog`
--

DROP TABLE IF EXISTS `catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalog` (
  `catalog_id` bigint NOT NULL AUTO_INCREMENT,
  `catalog_name` varchar(50) NOT NULL COMMENT '分类名称',
  `parent_id` bigint NOT NULL COMMENT '父级 id',
  `status` int DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`catalog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalog`
--

LOCK TABLES `catalog` WRITE;
/*!40000 ALTER TABLE `catalog` DISABLE KEYS */;
INSERT INTO `catalog` VALUES (1,'IT',0,0),(2,'test',1,0);
/*!40000 ALTER TABLE `catalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `common`
--

DROP TABLE IF EXISTS `common`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `common` (
  `common_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint NOT NULL COMMENT '文章 id',
  `user_id` bigint NOT NULL COMMENT '当前评论的用户',
  `content` text COMMENT '评论的内容',
  `common_date` datetime DEFAULT NULL COMMENT '评论的日期',
  `parent_id` bigint NOT NULL COMMENT '父级 id',
  `status` int DEFAULT '0' COMMENT '0 未删除 1 已删除',
  `reply` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`common_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common`
--

LOCK TABLES `common` WRITE;
/*!40000 ALTER TABLE `common` DISABLE KEYS */;
INSERT INTO `common` VALUES (2,7,1,'滚滚滚','2023-02-12 16:22:54',0,0,NULL),(4,7,1,'这篇文章写得太好了','2023-02-12 16:25:34',0,0,NULL),(5,7,1,'按着','2023-02-12 16:26:05',0,0,NULL),(12,7,2,'我也觉是','2023-02-13 17:31:06',4,1,NULL),(13,7,1,'O(∩_∩)O哈哈~','2023-02-13 17:35:42',4,1,'liming'),(14,4,2,'02.','2023-02-13 19:12:24',0,1,NULL);
/*!40000 ALTER TABLE `common` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_convert_add`
--

DROP TABLE IF EXISTS `file_convert_add`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file_convert_add` (
  `file_name` varchar(255) NOT NULL COMMENT '上传文件中间转换名字到添加',
  `file_unique` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_convert_add`
--

LOCK TABLES `file_convert_add` WRITE;
/*!40000 ALTER TABLE `file_convert_add` DISABLE KEYS */;
INSERT INTO `file_convert_add` VALUES ('2b7g7g4d6f0j1a0j5e3c0j5e3c4d7g7g.png','2b7g7g4d6f0j1a0j5e3c0j5e3c4d7g7g'),('2b2b6f5e4d9i7g1a8h0j0j9i4d5e7g9i.png','2b2b6f5e4d9i7g1a8h0j0j9i4d5e7g9i');
/*!40000 ALTER TABLE `file_convert_add` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '菜单名称',
  `icon` varchar(100) NOT NULL COMMENT '菜单图标',
  `parent_id` bigint NOT NULL COMMENT '父级 id',
  `level` bigint NOT NULL COMMENT '层级',
  `component` varchar(100) DEFAULT NULL COMMENT '组件',
  `enable` int DEFAULT '1' COMMENT '是否启用',
  `order_sort` bigint DEFAULT '0' COMMENT '排序',
  `status` bigint DEFAULT '1' COMMENT '状态',
  `path` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'主页','IEpMenu',0,1,'dashbard/index.vue',1,0,1,'dashbard'),(2,'笔记系统','IEpNotebook',0,1,NULL,1,1,1,''),(3,'笔记管理','IEpMemo',2,2,'article/index.vue',1,1,1,'notes/index'),(4,'layout','null',0,1,'layout',1,1,1,'/layout');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_role`
--

DROP TABLE IF EXISTS `menu_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_role` (
  `menu_role_id` bigint NOT NULL AUTO_INCREMENT,
  `menu_id` bigint NOT NULL COMMENT '菜单 id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  PRIMARY KEY (`menu_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_role`
--

LOCK TABLES `menu_role` WRITE;
/*!40000 ALTER TABLE `menu_role` DISABLE KEYS */;
INSERT INTO `menu_role` VALUES (1,1,1),(2,2,1),(3,3,1);
/*!40000 ALTER TABLE `menu_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'用户'),(2,'管理员');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swiper`
--

DROP TABLE IF EXISTS `swiper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `swiper` (
  `swiper_id` bigint NOT NULL AUTO_INCREMENT,
  `article_id` bigint NOT NULL COMMENT '文章 id',
  `img` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `enable` int DEFAULT '0' COMMENT '0 启用 1 禁用',
  `status` int DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`swiper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `swiper`
--

LOCK TABLES `swiper` WRITE;
/*!40000 ALTER TABLE `swiper` DISABLE KEYS */;
/*!40000 ALTER TABLE `swiper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(50) NOT NULL COMMENT '姓名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `follow` bigint DEFAULT '0' COMMENT '关注',
  `fans` bigint DEFAULT '0' COMMENT '粉丝',
  `pic` varchar(255) DEFAULT NULL COMMENT '头像',
  `status` int DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','123',0,0,'3c6f7g8h5e0j1a3c2b3c3c6f3c7g6f4d.webp',0),(2,'liming','123',1,2,'4d4d0j9i5e8h8h2b4d4d8h7g1a7g5e9i.webp',0),(3,'uuu','123',0,0,NULL,0),(4,'gg','gg',0,0,NULL,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_correlation`
--

DROP TABLE IF EXISTS `user_correlation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_correlation` (
  `uc_id` bigint NOT NULL AUTO_INCREMENT,
  `attention_user_id` bigint NOT NULL COMMENT '关注谁',
  `current_user_id` bigint NOT NULL COMMENT '哪个用户关注',
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`uc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_correlation`
--

LOCK TABLES `user_correlation` WRITE;
/*!40000 ALTER TABLE `user_correlation` DISABLE KEYS */;
INSERT INTO `user_correlation` VALUES (13,1,2,'2023-02-14 15:36:17'),(14,2,1,'2023-02-14 15:42:06');
/*!40000 ALTER TABLE `user_correlation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_role_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户 id',
  `role_id` bigint NOT NULL COMMENT '角色 id',
  `status` bigint DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,4,1,1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-25  8:53:44
