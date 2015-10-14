-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: projectsk
-- ------------------------------------------------------
-- Server version	5.6.24-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `f` varchar(150) NOT NULL,
  `i` varchar(150) NOT NULL,
  `o` varchar(150) DEFAULT NULL,
  `status` varchar(150) NOT NULL,
  `address` varchar(500) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (2,'Иванов','Иван','Иванович','свидетель','Петровка 38','+79856666666','111'),(4,'1','1','1','1','1','1','1'),(5,'aa','aaaaa','','','','','');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts_cases`
--

DROP TABLE IF EXISTS `contacts_cases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacts_cases` (
  `case_id` int(11) NOT NULL,
  `contact_id` int(11) NOT NULL,
  PRIMARY KEY (`contact_id`,`case_id`),
  KEY `FKED07E457CA21F3EA` (`case_id`),
  KEY `FKED07E457AD1AE5BA` (`contact_id`),
  CONSTRAINT `FKED07E457AD1AE5BA` FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`id`),
  CONSTRAINT `FKED07E457CA21F3EA` FOREIGN KEY (`case_id`) REFERENCES `criminalcase` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts_cases`
--

LOCK TABLES `contacts_cases` WRITE;
/*!40000 ALTER TABLE `contacts_cases` DISABLE KEYS */;
INSERT INTO `contacts_cases` VALUES (2,5);
/*!40000 ALTER TABLE `contacts_cases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criminalcase`
--

DROP TABLE IF EXISTS `criminalcase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `criminalcase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `description` mediumtext,
  `number` varchar(50) NOT NULL DEFAULT ' ',
  `article` varchar(1000) NOT NULL,
  `fact` varchar(1000) NOT NULL,
  `relation` varchar(1000) NOT NULL,
  `fabula` text NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `criminalcase_status_idx` (`status`),
  KEY `FK9CBC8E137BB55AF1` (`status`),
  CONSTRAINT `FK9CBC8E137BB55AF1` FOREIGN KEY (`status`) REFERENCES `status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criminalcase`
--

LOCK TABLES `criminalcase` WRITE;
/*!40000 ALTER TABLE `criminalcase` DISABLE KEYS */;
INSERT INTO `criminalcase` VALUES (1,'Первое дело','2014-06-13 14:35:43','По факту ограбления гражданина Иванова И.И.','255','111','ограбления гражданина Иванова И.И.','Петрова П.П.','фабула',1),(2,'Угон 02.06.2015','2015-06-01 00:00:00','По факту угона','234','224','угона','Захарова Александра Ивановича, дата рождения 22ю08б1969г., место рождения г.Томск','фабула',1),(3,'','2015-07-04 00:00:00','','345','','','','',NULL);
/*!40000 ALTER TABLE `criminalcase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docs`
--

DROP TABLE IF EXISTS `docs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `docs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `description` mediumtext,
  `parent` int(11) DEFAULT NULL,
  `file` longblob,
  PRIMARY KEY (`id`),
  KEY `FK2F223BAC514532` (`parent`),
  CONSTRAINT `FK2F223BAC514532` FOREIGN KEY (`parent`) REFERENCES `docs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docs`
--

LOCK TABLES `docs` WRITE;
/*!40000 ALTER TABLE `docs` DISABLE KEYS */;
INSERT INTO `docs` VALUES (1,'Касопределение ВС РФ от 30.11.2006 № 47-006-96 мотив личной заинтересованности.rtf','2015-08-03 19:02:12','Касопределение ВС РФ от 30.11.2006 № 47-006-96 мотив личной заинтересованности.rtf',9,NULL),(2,'Справочник ВОПРОСОВ по экспертизам Астрахань.pdf','2015-08-03 19:02:47','Справочник ВОПРОСОВ по экспертизам Астрахань.pdf',9,NULL),(3,'Комментарий к УК РФ- (постатейный)_В.....doc','2015-08-03 19:02:59','Комментарий к УК РФ- (постатейный)_В.....doc',9,NULL),(4,'Касопределение ВС РФ от 28.02.12 №53-012-6 лингвэкспертиза.rtf','2015-08-03 19:03:33','Касопределение ВС РФ от 28.02.12 №53-012-6 лингвэкспертиза.rtf',9,NULL);
INSERT INTO `docs` VALUES (6,'1.pdf','2015-08-03 19:04:29','1.pdf',10,NULL),(7,'2.pdf','2015-08-03 19:04:57','2.pdf',11,NULL),(8,'4.pdf','2015-08-03 19:05:18','4.pdf',11,NULL),(9,'Группа 1',NULL,NULL,NULL,NULL),(10,'Организационно-распорядительные документы СК России',NULL,NULL,NULL,NULL),(11,'Группа 3',NULL,NULL,10,NULL),(14,'Методические материалы',NULL,NULL,NULL,NULL),(15,'Справочная информация',NULL,NULL,NULL,NULL),(16,'Иное',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `docs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_types`
--

DROP TABLE IF EXISTS `event_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `color` bigint(20) NOT NULL DEFAULT '16777215',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_types`
--

LOCK TABLES `event_types` WRITE;
/*!40000 ALTER TABLE `event_types` DISABLE KEYS */;
INSERT INTO `event_types` VALUES (1,'Мероприятие',-578254593),(2,'Экспертиза',-578000045),(100,'Прочее',-573333297);
/*!40000 ALTER TABLE `event_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `description` text NOT NULL,
  `link_type` varchar(50) DEFAULT NULL,
  `link_object` int(11) DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `end_ldt` tinyblob,
  `linkObject` int(11) DEFAULT NULL,
  `linkType` varchar(255) DEFAULT NULL,
  `start_ldt` tinyblob,
  `type` int(11) NOT NULL DEFAULT '1',
  `responsible` int(11) NOT NULL DEFAULT '1',
  `сriminalcase` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `STATUS_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB307E119D7D68D8C` (`link_object`),
  KEY `events_type_idx` (`type`),
  KEY `FKB307E1194D0906E1` (`type`),
  KEY `FKB307E119E34B2FF` (`responsible`),
  KEY `FKB307E119653AC740` (`version`),
  KEY `FKB307E1196C91ED5` (`сriminalcase`),
  CONSTRAINT `FKB307E1194D0906E1` FOREIGN KEY (`type`) REFERENCES `event_types` (`id`),
  CONSTRAINT `FKB307E119653AC740` FOREIGN KEY (`version`) REFERENCES `versions` (`id`),
  CONSTRAINT `FKB307E1196C91ED5` FOREIGN KEY (`сriminalcase`) REFERENCES `criminalcase` (`id`),
  CONSTRAINT `FKB307E119D7D68D8C` FOREIGN KEY (`link_object`) REFERENCES `versions` (`id`),
  CONSTRAINT `FKB307E119E34B2FF` FOREIGN KEY (`responsible`) REFERENCES `responsibles` (`id`),
  CONSTRAINT `FK_ddpkx8owrb18wjwyyxt9bii1h` FOREIGN KEY (`link_object`) REFERENCES `versions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,'Допросить Каманову Д.Ф.','2015-06-21 08:00:00','Допросить свидетелй Каманову Д.Ф.\nПолучить справку о смерти Крылова А.М.',NULL,1,'2015-06-21 10:18:00',1,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw	\0\0�\n�x',NULL,NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0��x',1,1,1,0,NULL),(2,'Экспертиза орудия преступления','2015-07-01 00:00:00','Экспертиза орудия преступления',NULL,1,'2015-07-01 00:00:00',1,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0��x',NULL,NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0��x',2,1,1,0,NULL),(3,'Изъять записи с камер видеонаблюдения','2015-06-03 08:30:00','Неважная встреча.',NULL,1,'2015-06-03 12:30:00',1,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw	\0\0��x',NULL,NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw	\0\0��x',1,1,1,0,NULL),(6,'Проводить регулярный обход дома №12 по ул.Ленина','2015-06-21 10:00:00','Проводить регулярный обход дома','',1,'2015-06-21 11:10:56',1,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\n\0\0�\n�x',NULL,NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0��x',100,1,1,0,NULL),(7,'Дактилоскопическа судебная экспертиза','2015-06-24 15:00:01','Дактилоскопическа судебная экспертиза','',1,'2015-06-24 15:00:21',1,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\n\0\0�\0�x',NULL,NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\n\0\0�\0�x',2,1,1,0,NULL),(8,'1111','2015-07-05 01:18:48','1111111',NULL,NULL,'2015-07-05 01:18:48',NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0�0\Z��\0x',NULL,NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0�0\Z��\0x',1,1,1,0,NULL),(9,'2222','2015-07-05 02:04:50','22222',NULL,NULL,'2015-07-05 02:04:50',NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0�2`�@x',NULL,NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0�2`�@x',1,1,1,0,NULL),(10,'3333','2015-07-05 22:51:58','+3333',NULL,NULL,'2015-07-05 22:51:58',NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0�3:�)�x',NULL,NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0�3:�)�x',1,1,1,0,NULL),(12,'444','2015-07-05 23:11:25','444',NULL,NULL,'2015-07-05 23:11:25',NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0�-�\0x',NULL,NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0�-�\0x',1,1,1,0,NULL),(13,'555','2015-07-05 23:14:04','5555',NULL,NULL,'2015-07-05 23:14:04',5,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0�:��\0x',NULL,NULL,'��\0sr\0\rjava.time.Ser�]��\"H�\0\0xpw\0\0�:��\0x',1,1,1,0,NULL);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('versions',4),('criminalcase',2),('events',5);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materials`
--

DROP TABLE IF EXISTS `materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `materials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `description` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materials`
--

LOCK TABLES `materials` WRITE;
/*!40000 ALTER TABLE `materials` DISABLE KEYS */;
INSERT INTO `materials` VALUES (1,'11111',NULL,NULL),(2,'22222',NULL,NULL);
/*!40000 ALTER TABLE `materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otherthings`
--

DROP TABLE IF EXISTS `otherthings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otherthings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `description` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otherthings`
--

LOCK TABLES `otherthings` WRITE;
/*!40000 ALTER TABLE `otherthings` DISABLE KEYS */;
/*!40000 ALTER TABLE `otherthings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planing`
--

DROP TABLE IF EXISTS `planing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `planing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `criminalcase` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `text` text NOT NULL,
  `performer` varchar(250) NOT NULL DEFAULT 'Лично',
  `start` datetime DEFAULT NULL,
  `done` datetime DEFAULT NULL,
  `importance` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planing`
--

LOCK TABLES `planing` WRITE;
/*!40000 ALTER TABLE `planing` DISABLE KEYS */;
/*!40000 ALTER TABLE `planing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` mediumtext,
  `topic_id` int(11) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK65E7BD3D4F3DEAD` (`topic_id`),
  CONSTRAINT `FK65E7BD3D4F3DEAD` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'Это пример сообщения топика.',1,'','2015-07-14 12:19:46'),(2,'1. Либо открыть уголовное дело, и добавить мероприятие с привязкой к версии на закладке &quot;Планирование&quot;\n2. То же самое, но без привязки к версии.\n3. Из календаря.',2,'','2015-07-14 12:27:31'),(3,'!!!!!',1,'','1970-01-01 04:00:00'),(4,'444444444',1,'','1970-01-01 04:00:00'),(5,'55555555',1,'1884022442','1970-01-01 04:00:00'),(8,'888888888',1,'Иван','1970-01-01 04:00:00'),(9,'приветьььььььььььььььььььььььььььььььььььььььььььььььььььь  ппппппппппп',2,'Иван','1970-01-01 04:00:00'),(10,'9999999',1,'Петров','1970-01-01 04:00:00'),(11,'555555',1,'Петров','1970-01-01 04:33:35'),(12,'777',1,'Петров','1970-01-01 04:33:35'),(13,'666',1,'Петров','2015-08-17 19:25:32'),(14,'ddddddddd',2,'Петров','2015-08-17 19:33:26'),(15,'ddddddddd',2,'Петров','2015-08-17 19:33:31'),(16,'ddddddddd',2,'Петров','2015-08-17 19:33:33'),(17,'88888',1,'Петров','2015-08-18 12:54:42'),(18,'9999',1,'Петров','2015-08-18 12:54:48'),(19,'000000000\r\n',1,'Петров','2015-08-18 12:55:10'),(20,'000000000\r\n4544\r\n45455464\r\n',1,'Петров','2015-08-18 12:55:17'),(21,'01',1,'904932396','2015-08-18 13:07:23'),(22,'02',1,'904932396','2015-08-18 13:07:28'),(23,'03',1,'904932396','2015-08-18 13:07:31'),(24,'04',1,'904932396','2015-08-18 13:07:35');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `responsibles`
--

DROP TABLE IF EXISTS `responsibles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `responsibles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responsibles`
--

LOCK TABLES `responsibles` WRITE;
/*!40000 ALTER TABLE `responsibles` DISABLE KEYS */;
INSERT INTO `responsibles` VALUES (1,'Лично'),(2,'МВД'),(3,'ЭКЦ МВД');
/*!40000 ALTER TABLE `responsibles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'Возбуждено',''),(2,'Приостановлено',''),(3,'Возобновлено',''),(4,'Прекращено','');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_change`
--

DROP TABLE IF EXISTS `status_change`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `criminalcase` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `done` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7AFECC9D45CB9973` (`criminalcase`),
  KEY `FK7AFECC9D7BB55AF1` (`status`),
  CONSTRAINT `FK7AFECC9D45CB9973` FOREIGN KEY (`criminalcase`) REFERENCES `criminalcase` (`id`),
  CONSTRAINT `FK7AFECC9D7BB55AF1` FOREIGN KEY (`status`) REFERENCES `status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_change`
--

LOCK TABLES `status_change` WRITE;
/*!40000 ALTER TABLE `status_change` DISABLE KEYS */;
INSERT INTO `status_change` VALUES (1,1,1,'2015-06-01 12:04:18'),(2,1,2,'2015-06-02 12:04:31');
/*!40000 ALTER TABLE `status_change` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplementalinformation`
--

DROP TABLE IF EXISTS `supplementalinformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplementalinformation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `description` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplementalinformation`
--

LOCK TABLES `supplementalinformation` WRITE;
/*!40000 ALTER TABLE `supplementalinformation` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplementalinformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics`
--

DROP TABLE IF EXISTS `topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1024) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCC42D9248969AD52` (`parent_id`),
  CONSTRAINT `FKCC42D9248969AD52` FOREIGN KEY (`parent_id`) REFERENCES `topics` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (1,'Добро пожаловать в форум',NULL),(2,'Как добавить мероприятие',NULL);
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `versions`
--

DROP TABLE IF EXISTS `versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `versions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `criminalcase` int(11) NOT NULL,
  `number` int(11) DEFAULT NULL,
  `text` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `versions_criminalcase_idx` (`criminalcase`),
  KEY `FK89AE7E9B45CB9973` (`criminalcase`),
  CONSTRAINT `FK89AE7E9B45CB9973` FOREIGN KEY (`criminalcase`) REFERENCES `criminalcase` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `versions`
--

LOCK TABLES `versions` WRITE;
/*!40000 ALTER TABLE `versions` DISABLE KEYS */;
INSERT INTO `versions` VALUES (1,1,222,'Ранее судимым лицом'),(2,1,333,'Лицом из круга общения потерпевшего'),(3,1,3,'111'),(4,2,1,'111'),(5,1,4,'2222');
/*!40000 ALTER TABLE `versions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-08-19 12:16:27
-- 98656750
-- Семенов