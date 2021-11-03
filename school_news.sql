
--
-- Table structure for table `t_news`
--

DROP TABLE IF EXISTS `t_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻id',
  `title` varchar(255) NOT NULL COMMENT '新闻标题',
  `author` varchar(255) NOT NULL COMMENT '作者',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `picture_url` varchar(255) DEFAULT NULL COMMENT '图片',
  `type_id` int(11) NOT NULL COMMENT '外键关联新闻类型表主键',
  `user_id` int(11) NOT NULL COMMENT '外键关联用户表主键',
  `issue_time` datetime NOT NULL COMMENT '发布时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int(11) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `news_type_fk` (`type_id`),
  KEY `news_user_fk` (`user_id`),
  CONSTRAINT `news_type_fk` FOREIGN KEY (`type_id`) REFERENCES `t_news_type` (`type_id`),
  CONSTRAINT `news_user_fk` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_news`
--

LOCK TABLES `t_news` WRITE;
/*!40000 ALTER TABLE `t_news` DISABLE KEYS */;
INSERT INTO `t_news` (`id`, `title`, `author`, `content`, `picture_url`, `type_id`, `user_id`, `issue_time`, `update_time`, `status`) VALUES (1,'老王','老王','# 老王是一个有故事的人\n```java\npublic static void mian(){\n    system.out.println(\"Hello World!\");\n}\n```\n\n陈金龙教授现任华南师范大学马克思主义学院院长，教授，博士生导师，历史学博士，教育部“长江学者”特聘教授，国家“万人计划”领军人才，省委党史学习教育宣讲团成员，享受国务院政府特殊津贴，主要从事中共党史、马克思主义中国化研究。','http://localhost:8081/files/c8e4d5f7f3a94016ae92b604b262a702',2,1,'2021-07-25 08:00:00','2021-07-29 21:27:37',1),(2,'老王有几个妻子','老王的妻子','当然只有一个，因为我国的法律规定一夫一妻制','http://localhost:8081/files/e3a7d7a2b2274b7bbee07bdeb3054f4e',2,1,'2021-07-25 08:00:00','2021-07-29 21:27:52',1),(4,'老王同学1','同学','432当然只有一个，因为我国的法律规定一夫一妻制','http://localhost:8081/files/4a6904068a304d3593164759571265f4',2,1,'2021-07-25 08:00:00','2021-07-29 21:35:19',1),(5,'隔壁','王','密秘','http',2,1,'2021-07-27 13:19:09','2021-07-27 13:19:11',1),(6,'赤壁之战','刘备','大获全胜','http',2,1,'2021-07-27 13:19:09','2021-07-27 13:19:11',1),(7,'败走麦城','关羽','命不久矣','http',2,1,'2021-07-27 13:19:09','2021-07-27 13:19:11',1),(8,'长坂坡之战111','赵云','为救阿斗，战遍群雄','http',1,1,'2021-07-27 08:00:00','2021-07-29 14:20:45',1),(9,'gggg1','a','j56fwe','http://localhost:8081/files/808843cabefc434384e92382d0ea0a9a',2,1,'2021-07-27 08:00:00','2021-07-29 21:35:44',1),(14,'fwefwef','ffff','fwf',NULL,2,1,'2021-07-29 21:37:28',NULL,1);
/*!40000 ALTER TABLE `t_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_news_type`
--

DROP TABLE IF EXISTS `t_news_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_news_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻类型id',
  `type_name` varchar(255) NOT NULL COMMENT '新闻类型名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_news_type`
--

LOCK TABLES `t_news_type` WRITE;
/*!40000 ALTER TABLE `t_news_type` DISABLE KEYS */;
INSERT INTO `t_news_type` (`type_id`, `type_name`, `create_time`) VALUES (1,'政治要闻','2021-07-24 23:37:01'),(2,'生活日常','2021-07-24 23:37:23'),(3,'军事要闻','2021-07-24 23:38:48');
/*!40000 ALTER TABLE `t_news_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表主键id',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `status` int(11) NOT NULL COMMENT '状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `role_id` int(11) NOT NULL COMMENT '1:管理员，2:编辑人员，3：普通用户',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `t_user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`user_id`, `username`, `password`, `email`, `nick_name`, `status`, `create_time`, `update_time`, `role_id`) VALUES (1,'t','t','t@.qq.com','管理员',1,'2021-07-24 08:00:00','2021-07-28 14:43:58',1),(2,'f','f',NULL,'关羽',1,'2021-07-25 08:00:00',NULL,2),(3,'f1','f1',NULL,'张飞',1,'2021-07-25 08:00:00',NULL,3),(5,'tt','tt','','sh',1,'2021-07-27 08:00:00','2021-07-27 19:10:46',2),(7,'f11','f',NULL,NULL,1,'2021-07-28 15:16:49',NULL,3),(8,'y','y',NULL,NULL,1,'2021-07-28 17:04:49',NULL,3),(9,'x','x','xx','x',1,'2021-07-29 08:00:00','2021-07-29 14:18:17',2),(11,'q','q','qq','q',1,'2021-07-29 08:00:00','2021-07-29 21:36:39',2);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-29 21:58:34
