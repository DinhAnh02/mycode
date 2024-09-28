-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 100.67.168.11    Database: vks-db
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG`
--

LOCK TABLES `DATABASECHANGELOG` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG` VALUES ('1','admin','db/changelog/db.changelog-master.xml','2024-09-28 11:16:05',39,'RERAN','9:c16a447224d7b2199866a7d31b1809bb','sqlFile path=classpath:db/data/database_VKS.sql','',NULL,'4.27.0',NULL,NULL,'7522164352'),('2','admin','db/changelog/db.changelog-master.xml','2024-09-27 01:27:58',2,'EXECUTED','9:07b0793f00c0a301214518839906abbf','tagDatabase','','import-complete-v1','4.27.0',NULL,NULL,'7400477710'),('3','admin','db/changelog/db.changelog-master.xml','2024-09-27 01:27:58',3,'EXECUTED','9:01d352ad6dc48d7c131a745581533bf6','sql','',NULL,'4.27.0',NULL,NULL,'7400477710');
/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int NOT NULL,
  `LOCKED` tinyint NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOGLOCK`
--

LOCK TABLES `DATABASECHANGELOGLOCK` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOGLOCK` VALUES (1,0,NULL,NULL);
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_case`
--

DROP TABLE IF EXISTS `account_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_case` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_role` varchar(255) DEFAULT NULL,
  `has_access` bit(1) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `account_id` bigint NOT NULL,
  `case_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkfl6gg20wd213yjpm9e1hpug9` (`account_id`),
  KEY `FKelewer2rckbgbg0u3r6dvtdsg` (`case_id`),
  CONSTRAINT `FKelewer2rckbgbg0u3r6dvtdsg` FOREIGN KEY (`case_id`) REFERENCES `cases` (`id`),
  CONSTRAINT `FKkfl6gg20wd213yjpm9e1hpug9` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_case`
--

LOCK TABLES `account_case` WRITE;
/*!40000 ALTER TABLE `account_case` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_condition_login1` bit(1) DEFAULT NULL,
  `is_condition_login2` bit(1) DEFAULT NULL,
  `is_connect_computer` bit(1) DEFAULT NULL,
  `is_connect_usb` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `pin` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `department_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn25fcmdgthptgvop4f4sqs6sc` (`department_id`),
  KEY `FKt3wava8ssfdspnh3hg4col3m1` (`role_id`),
  CONSTRAINT `FKn25fcmdgthptgvop4f4sqs6sc` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`),
  CONSTRAINT `FKt3wava8ssfdspnh3hg4col3m1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'2024-09-23 18:48:40.000000','devTuan',_binary '\0',_binary '\0',_binary '\0',_binary '\0','$2a$10$Fz5QEKpAVIi3GQSZiHcI2.lEqtskxIVJDD/thR9WLT2u.CVMm.Y6i',NULL,'ACTIVE','2024-09-24 23:15:18.355717','devTuan','system',2,3),(2,'2024-09-23 18:48:40.000000','MCB003',_binary '\0',_binary '\0',_binary '',_binary '','$2a$10$cg9s.HOewBfL8QtPRUUaP.SBhMH0Obf.hfdezLy8wUMLNFIew0XyW',NULL,'ACTIVE','2024-09-24 15:59:45.285207','MCB003','MCB002',1,1),(3,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB003',1,2),(4,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB004',3,4),(5,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB005',4,4),(6,'2024-09-23 18:48:40.000000','MCB010',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','INACTIVE','2024-09-24 15:50:38.355517','MCB001','MCB006',5,4),(7,'2024-09-23 18:48:40.000000','MCB010',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','INACTIVE','2024-09-24 15:37:08.748398','MCB001','MCB007',6,4),(8,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB008',7,4),(9,'2024-09-23 18:48:40.000000','MCB003',_binary '\0',_binary '\0',_binary '\0',_binary '\0','$2a$10$b6wmGWvzTYiGf583f/Nf3OpJCEQnzxuKXkGMYx6N5tt118VmvgyXK',NULL,'INACTIVE','2024-09-24 16:04:11.483456','MCB003','MCB009',8,4),(10,'2024-09-23 18:48:40.000000','MCB003',_binary '\0',_binary '\0',_binary '\0',_binary '\0','$2a$10$DO72Ms3coQbmr4iwtDH2F.Cf3dBIrBAbcEZl0R1iRivZKomx9.PR2',NULL,'INACTIVE','2024-09-24 16:07:17.571455','MCB003','MCB010',9,4),(11,'2024-09-23 18:48:40.000000','MCB013',_binary '',_binary '',_binary '',_binary '\0','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-24 10:53:38.408113','MCB001','MCB011',10,4),(12,'2024-09-23 18:48:40.000000','MCB003',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-24 16:23:41.235228','MCB003','MCB012',3,5),(13,'2024-09-23 18:48:40.000000','MCB003',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','INACTIVE','2024-09-24 16:14:05.960476','MCB001','MCB013',4,5),(14,'2024-09-23 18:48:40.000000','MCB013',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','INACTIVE','2024-09-24 11:21:12.166984','MCB001','MCB014',5,5),(15,'2024-09-23 18:48:40.000000','MCB013',_binary '\0',_binary '\0',_binary '',_binary '','$2a$10$XwnhHGR55vbag6BLeRtxH.9XN4FhAHQeBo1xAZEtDkddrAOi7/w6u',NULL,'ACTIVE','2024-09-24 16:00:38.518819','MCB013','MCB015',6,5),(16,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB016',7,5),(17,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB017',8,5),(18,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB018',9,5),(19,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB019',10,5),(20,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB020',3,6),(21,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB021',3,6),(22,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB022',3,6),(23,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB023',3,6),(24,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB024',3,6),(25,'2024-09-23 18:48:40.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:40.000000','MCB001','MCB025',3,6),(26,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB026',3,6),(27,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB027',3,6),(28,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB028',3,6),(29,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB029',4,6),(30,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB030',4,6),(31,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB031',4,6),(32,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB032',4,6),(33,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB033',4,6),(34,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB034',4,6),(35,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB035',4,6),(36,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB036',4,6),(37,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB037',4,6),(38,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB038',5,6),(39,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB039',5,6),(40,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB040',5,6),(41,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB041',5,6),(42,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB042',5,6),(43,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB043',5,6),(44,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB044',5,6),(45,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB045',5,6),(46,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB046',6,6),(47,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB047',6,6),(48,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB048',6,6),(49,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB049',6,6),(50,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB050',6,6),(51,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB051',6,6),(52,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB052',6,6),(53,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB053',6,6),(54,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB054',7,6),(55,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB055',7,6),(56,'2024-09-23 18:48:41.000000','MCB013',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','INACTIVE','2024-09-24 15:19:02.716349','MCB001','MCB056',7,6),(57,'2024-09-23 18:48:41.000000','MCB013',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-24 15:45:32.322735','MCB013','MCB057',7,6),(58,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB058',7,6),(59,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB059',7,6),(60,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB060',7,6),(61,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB061',7,6),(62,'2024-09-23 18:48:41.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:41.000000','MCB001','MCB062',8,6),(63,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB063',8,6),(64,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB064',8,6),(65,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB065',8,6),(66,'2024-09-23 18:48:42.000000','MCB010',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','INACTIVE','2024-09-24 15:29:21.102581','MCB001','MCB066',8,6),(67,'2024-09-23 18:48:42.000000','MCB010',_binary '\0',_binary '\0',_binary '',_binary '','$2a$10$iy7gkkqrKVbquvQc6NbzgO86h96lpjby2l0YjvWdeUjUTPT8dSX5i',NULL,'ACTIVE','2024-09-24 15:32:30.148637','MCB010','MCB067',8,6),(68,'2024-09-23 18:48:42.000000','MCB013',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-24 16:29:34.778772','MCB013','MCB068',8,6),(69,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB069',8,6),(70,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB070',9,6),(71,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB071',9,6),(72,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB072',9,6),(73,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB073',9,6),(74,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB074',9,6),(75,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB075',9,6),(76,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB076',9,6),(77,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB077',9,6),(78,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB078',10,6),(79,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB079',10,6),(80,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB080',10,6),(81,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB081',10,6),(82,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB082',10,6),(83,'2024-09-23 18:48:42.000000','MCB001',_binary '',_binary '',_binary '',_binary '','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-23 18:48:42.000000','MCB001','MCB083',10,6),(84,'2024-09-23 18:48:42.000000','MCB002',_binary '',_binary '',_binary '',_binary '\0','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-24 10:25:08.378511','MCB001','MCB084',10,6),(85,'2024-09-23 18:48:42.000000','MCB002',_binary '',_binary '',_binary '',_binary '\0','$2a$10$8Yik.2jLyF13gfpvlSPW5.86qC8O69w4MyH/irquZlV8cYNnEwmbq','$2a$10$v3hxRtjF3ZKSRrcz6G/PJ.dzO0AN7M8v4v674rtNhXF9kM4o6kP4G','ACTIVE','2024-09-24 10:13:48.973332','MCB001','MCB085',10,6),(86,'2024-09-23 19:21:09.000000','devTuan',_binary '',_binary '',_binary '',_binary '','$2a$10$.KlB6ZXj0jYJCFSQGStMaOjNPG8F3/slaf2pCUG/OouedAI1jdpO2','$2a$10$lnp519a5qLdS.IZRY/8FR.JX.YaRfFaFS8RUY3iWcRSf0mV1d0dbO','ACTIVE','2024-09-24 18:11:20.277262','system','devTuan',2,3),(87,'2024-09-23 19:31:55.000000','system',_binary '',_binary '',_binary '',_binary '','$2a$10$5x5WNlAmpYaEb0LcAO9Z.e6kwZqym.0YqqE4aCCyslZQkc1IsWDv6','$2a$10$NR9wBKUjI9XrRiC1H1Mrv.WFXlvn4f5SecizUn4S6SqVxn1yId01K','ACTIVE','2024-09-23 19:31:55.000000','system','devFE01',2,3),(88,'2024-09-23 19:33:09.000000','system',_binary '',_binary '',_binary '',_binary '','$2a$10$DemXrbcyBeI0DSs.Yv0QM.o5dxDcnqCijBgtLerC.PM/QF8lEGdh.','$2a$10$BBB9Fn51uMiudVJW5Hxace7gciu1DNRxDPjdcAaZdhiszfkr91/4y','ACTIVE','2024-09-23 19:33:09.000000','system','devBE01',2,3),(89,'2024-09-23 19:35:57.000000','system',_binary '',_binary '',_binary '',_binary '','$2a$10$8zmveNXWLUTZdXZlBzkBUuQ1DrSIdx8fMdy/MNkpHRvwxjH.lofG2','2a$10$MWqUkj.v0PEzOF6dv5cGD.xIMT5k7amB9L3zEUjGMeqHV8pFs.DNO','ACTIVE','2024-09-23 19:35:57.000000','system','devBE02',2,1),(90,'2024-09-23 19:52:22.000000','system',_binary '',_binary '',_binary '',_binary '','$2a$10$OfCt3Xax63pYfU0uWvR9Ye9PHjsrctbi0aM.K9eZbi98EubG31CcK','$2a$10$3sH2cNK/Lvrfuad06sc7su.Hd.8Mta7KWenUYElRgOttZjQyVZJ0q','ACTIVE','2024-09-23 19:52:22.000000','system','devFE02',2,3),(92,'2024-09-23 19:54:30.000000','system',_binary '',_binary '',_binary '',_binary '','$2a$10$74qGmV8k6FEYRHw9nCza9eVZJR9JXX7SHA5zDoQWKNBkBmPd0zqYS','$2a$10$JXS7TW.k0i0m3OpFKfDOnOCpXP6ARKQ2Gn18KUB4J0jxsqrxKbkWW','ACTIVE','2024-09-23 19:54:30.000000','system','devFE03',2,3),(93,'2024-09-25 03:08:19.000000','system',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$pM5tgFrIr/ziMtyTSw5FI.jLjotbemjTG6ImktVNMfcj1cQ5NVsFC','$2a$10$tk3.biNa7GaulVp5G7xJvuXmEHXd8ugZ.C9AlkXEkrsMDj7DiucWy','ACTIVE','2024-09-25 03:08:19.000000','system','devVuong',2,3),(94,'2024-09-25 03:09:45.000000','system',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$pbOLMv7PYeIz01uaqFEiQu3Vj1sfC7oeziSIRGrp9DUA33sLGd3WK','$2a$10$0s6bAwUeEamAhrl3YfP1jONQPatbqvsq1bkhC/Wm2BvMCujsbAOr6','ACTIVE','2024-09-25 03:09:45.000000','system','devHieu',2,3),(95,'2024-09-25 03:10:47.000000','system',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$LUIWaM4R5C9K8WKHbw7X3u2dKvnbt/ODENwuOwAUPzpxXZ47qpy7y','$2a$10$WDle2Pyo82TCe2vXznKT1.nEEaC4qPul9blo295IlrILDP3kBmUDe','ACTIVE','2024-09-25 03:10:47.000000','system','devManh',2,3),(96,'2024-09-25 03:12:17.000000','system',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$y20Wa5N97MbOoF81R4hPKu86RQQuD302nExLNj8cPcx7f5Io1Tvnq','$2a$10$qvwQiQWujas/ny/RW96x/uqTnQezyPszl.yug/eNcbjQ6C/4BvRvS','ACTIVE','2024-09-25 03:12:17.000000','system','devCuong',2,3),(97,'2024-09-25 03:13:28.000000','system',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$aQzkrbD5qOihKP4sf8IRmOgUBlNkNB4/gZySDIAcpZWLI0oAkbzb6','$2a$10$Xn3bvG.7ZvNidwlrOm90U.VngmrV0bZgHW23pisI.y/PW66ucR0Ey','ACTIVE','2024-09-25 03:13:28.000000','system','devThang',2,3),(98,'2024-09-25 03:14:36.000000','system',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$OgIAhYXp.TL1d5Y13rkYMO0nyD/e6cWMVMSDj7zAhwyZbatzL89wC','$2a$10$3RghZtWu5ZmVDEeIS8S/ZOPhuGsZ.Mv.8p8rxXpoBZfk2cbVynfci','ACTIVE','2024-09-25 03:14:36.000000','system','devGiang',2,3),(99,'2024-09-25 03:15:31.000000','system',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$YCY3ZZPK62mQmnBm0gFJYuWRTfv4ZM8y/QUm8FT8/aS4zd9mfYb/.','$2a$10$dGmCkt3NBDwFPGGrDiHCOOQkCW9NKOj1.p/aa4SzbZ49iMmuqKb/C','ACTIVE','2024-09-25 03:15:31.000000','system','devDat',2,3),(100,'2024-09-25 03:22:14.000000','system',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$gD1bI9X6m51JOJbJKwskXuzzP1wKvmJcC20KD7IrHWcv5DWbteniO','$2a$10$bMU37TC89Ffss5RcKbNX0uMmhh4ZvyZbgs5aVEZrZyMQnzMRCfceu','ACTIVE','2024-09-25 03:22:14.000000','system','devHung',2,3),(101,'2024-09-25 03:23:01.000000','system',_binary '',_binary '',_binary '\0',_binary '\0','$2a$10$Fv8KKvYz0vCOLVbYLcGWgOBrYA9eSV2xia79HDTzV7N6A6CYbPDre','$2a$10$dOly8S9mtwW9ZA11sP9nIOCgXtvklg761MAIpT7mzPB2EDt8NSdfK','ACTIVE','2024-09-25 03:23:01.000000','system','devDan',2,3);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authtokens`
--

DROP TABLE IF EXISTS `authtokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authtokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `is_expire_time` bit(1) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_type` varchar(255) DEFAULT NULL,
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqyxtn15qtaxd1xaftiiwrx50a` (`account_id`),
  CONSTRAINT `FKqyxtn15qtaxd1xaftiiwrx50a` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=310 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authtokens`
--

LOCK TABLES `authtokens` WRITE;
/*!40000 ALTER TABLE `authtokens` DISABLE KEYS */;
INSERT INTO `authtokens` VALUES (25,'2024-09-24 14:26:38.406854',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiS0lFTV9TQVRfVklFTiIsImtleVVzYiI6ImVlNmEyOTlkLTc2ZmQtMTFlZi04ODRjLTAyNDJhYzEyMDAwMiIsInN1YiI6Ik1DQjAyMCIsImlhdCI6MTcyNzE2Mjc5OCwiZXhwIjoxNzI3MjQ5MTk4fQ.QHl8LWactT8yhv-lxbYnoXwTWYgs_vvka8lv1zY0QGw','ACCESS',20),(27,'2024-09-24 14:50:35.307848',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVFJVT05HX1BIT05HIiwia2V5VXNiIjoiZGZhMWRkYzAtNzZmZi0xMWVmLTg4NGMtMDI0MmFjMTIwMDAyIiwic3ViIjoiTUNCMDA0IiwiaWF0IjoxNzI3MTY0MjM1LCJleHAiOjE3MjcyNTA2MzV9.DJvuNeAftfPw0gJjagPbJVXjgniSouimDTTZzOJBKzU','ACCESS',4),(29,'2024-09-24 15:18:27.137014',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUEhPX1BIT05HIiwia2V5VXNiIjoiZWU2YTE4OTctNzZmZC0xMWVmLTg4NGMtMDI0MmFjMTIwMDAyIiwic3ViIjoiTUNCMDEzIiwiaWF0IjoxNzI3MTY1OTA3LCJleHAiOjE3MjcyNTIzMDd9.j4HcufdlKGuxGZySoC1JZM_EeHy2fEUKaEm6bo3UHpg','ACCESS',13),(30,'2024-09-24 15:21:56.919991',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiS0lFTV9TQVRfVklFTiIsImtleVVzYiI6ImVlNmEyZmU1LTc2ZmQtMTFlZi04ODRjLTAyNDJhYzEyMDAwMiIsInN1YiI6Ik1DQjAyNSIsImlhdCI6MTcyNzE2NjExNiwiZXhwIjoxNzI3MjUyNTE2fQ.tNriwGHp2TquSXHBHxuPLqW_zjoSG5uvujSeTal4ed8','ACCESS',25),(33,'2024-09-24 16:16:42.350910',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVklFTl9QSE8iLCJrZXlVc2IiOiJkZmExZGJkMS03NmZmLTExZWYtODg0Yy0wMjQyYWMxMjAwMDIiLCJzdWIiOiJNQ0IwMDMiLCJpYXQiOjE3MjcxNjk0MDIsImV4cCI6MTcyNzI1NTgwMn0.LEJwzjHmLk1GZBQb-TfacBTpIv1Y7d9VgCB8mO31CbI','ACCESS',3),(278,'2024-09-25 10:46:05.153568',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUEhPX1BIT05HIiwia2V5VXNiIjoiZWU2YTE2NDctNzZmZC0xMWVmLTg4NGMtMDI0MmFjMTIwMDAyIiwic3ViIjoiTUNCMDEyIiwiaWF0IjoxNzI3MjM1OTY1LCJleHAiOjE3MjczMjIzNjV9.jCJ1XBEc12L1SFyFlAp1wuwa8F3CNhWbPvnX-yeydbQ','ACCESS',12),(279,'2024-09-25 10:52:33.736428',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiS0lFTV9TQVRfVklFTiIsImtleVVzYiI6IjBjZjM0NzE4LTc2ZmUtMTFlZi04ODRjLTAyNDJhYzEyMDAwMiIsInN1YiI6Ik1DQjA2MiIsImlhdCI6MTcyNzIzNjM1MywiZXhwIjoxNzI3MzIyNzUzfQ.2yKWSLuz9drMCmc2zPH9ASVoV_-Av74J4FrtDHCc_qQ','ACCESS',62),(295,'2024-09-25 16:01:17.678121',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiSVRfQURNSU4iLCJrZXlVc2IiOiI2YzA2ZTUxYi03NzAwLTExZWYtODg0Yy0wMjQyYWMxMjAwMDIiLCJzdWIiOiJkZXZNYW5oIiwiaWF0IjoxNzI3MjU0ODc3LCJleHAiOjE3MjczNDEyNzd9.slQCPlDo60yicXMzX4Xm2A5GvB0D7_9yppeknOA3BG4','ACCESS',95),(299,'2024-09-25 16:46:36.066215',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiSVRfQURNSU4iLCJrZXlVc2IiOiI2YzA2ZTUxYi03NzAwLTExZWYtODg0Yy0wMjQyYWMxMjAwMDIiLCJzdWIiOiJkZXZIdW5nIiwiaWF0IjoxNzI3MjU3NTk2LCJleHAiOjE3MjczNDM5OTZ9.NuAxt2HKfzYT-68eDfypaPXQDgUp2ECcMMnrhu4-Liw','ACCESS',100),(301,'2024-09-25 17:00:33.513934',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiSVRfQURNSU4iLCJrZXlVc2IiOiI2YzA2ZTUxYi03NzAwLTExZWYtODg0Yy0wMjQyYWMxMjAwMDIiLCJzdWIiOiJkZXZUaGFuZyIsImlhdCI6MTcyNzI1ODQzMywiZXhwIjoxNzI3MzQ0ODMzfQ.fdnJbA_h9n1hK0J2v8zIjow12Q2cZTXsedKGn07BkcI','ACCESS',97),(304,'2024-09-25 17:20:25.404011',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiSVRfQURNSU4iLCJrZXlVc2IiOiI2YzA2ZTUxYi03NzAwLTExZWYtODg0Yy0wMjQyYWMxMjAwMDIiLCJzdWIiOiJkZXZCRTAxIiwiaWF0IjoxNzI3MjU5NjI1LCJleHAiOjE3MjczNDYwMjV9.5yellBS6E_hHFSU53gQu3p8gObjwa0LKCucxtqliBQ4','ACCESS',88),(306,'2024-09-25 17:31:10.996459',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVklFTl9UUlVPTkciLCJrZXlVc2IiOiI2YzA2ZTUxYi03NzAwLTExZWYtODg0Yy0wMjQyYWMxMjAwMDIiLCJzdWIiOiJkZXZCRTAyIiwiaWF0IjoxNzI3MjYwMjcwLCJleHAiOjE3MjczNDY2NzB9.BAHnL_jOdsgrvh62zYkhPxi_EvoZA9t1KaTHADodDVo','ACCESS',89),(309,'2024-09-28 18:37:06.480770',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiSVRfQURNSU4iLCJrZXlVc2IiOiI2YzA2ZTUxYi03NzAwLTExZWYtODg0Yy0wMjQyYWMxMjAwMDIiLCJzdWIiOiJkZXZUdWFuIiwiaWF0IjoxNzI3NTIzNDI2LCJleHAiOjE3Mjc2MDk4MjZ9.LZmaJ9vfJswg4T9aa-Z8-ei1eJRzHHmb3QPzvsqVT9w','ACCESS',86);
/*!40000 ALTER TABLE `authtokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `case_person`
--

DROP TABLE IF EXISTS `case_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `case_person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_delete` bit(1) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `case_id` bigint NOT NULL,
  `citizen_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5d9ntw617ncv4b3wjjc9mke35` (`case_id`),
  KEY `FKtkk51fbdgmhbggbm1p94sbb9u` (`citizen_id`),
  CONSTRAINT `FK5d9ntw617ncv4b3wjjc9mke35` FOREIGN KEY (`case_id`) REFERENCES `cases` (`id`),
  CONSTRAINT `FKtkk51fbdgmhbggbm1p94sbb9u` FOREIGN KEY (`citizen_id`) REFERENCES `citizens` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `case_person`
--

LOCK TABLES `case_person` WRITE;
/*!40000 ALTER TABLE `case_person` DISABLE KEYS */;
/*!40000 ALTER TABLE `case_person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `case_status`
--

DROP TABLE IF EXISTS `case_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `case_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `case_status`
--

LOCK TABLES `case_status` WRITE;
/*!40000 ALTER TABLE `case_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `case_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caseflow`
--

DROP TABLE IF EXISTS `caseflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caseflow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `case_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtjaojn5g839mom0p0vilthwql` (`case_id`),
  CONSTRAINT `FKtjaojn5g839mom0p0vilthwql` FOREIGN KEY (`case_id`) REFERENCES `cases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caseflow`
--

LOCK TABLES `caseflow` WRITE;
/*!40000 ALTER TABLE `caseflow` DISABLE KEYS */;
/*!40000 ALTER TABLE `caseflow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cases`
--

DROP TABLE IF EXISTS `cases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cases` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `case_type` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `case_status_id` bigint NOT NULL,
  `department_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKign6ooton1r03flvh5excbh2y` (`case_status_id`),
  KEY `FKm7pnvrbaih7w9n8nagmp0b915` (`department_id`),
  CONSTRAINT `FKign6ooton1r03flvh5excbh2y` FOREIGN KEY (`case_status_id`) REFERENCES `case_status` (`id`),
  CONSTRAINT `FKm7pnvrbaih7w9n8nagmp0b915` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cases`
--

LOCK TABLES `cases` WRITE;
/*!40000 ALTER TABLE `cases` DISABLE KEYS */;
/*!40000 ALTER TABLE `cases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `citizens`
--

DROP TABLE IF EXISTS `citizens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citizens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `identification` varchar(255) DEFAULT NULL,
  `investigator_code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citizens`
--

LOCK TABLES `citizens` WRITE;
/*!40000 ALTER TABLE `citizens` DISABLE KEYS */;
/*!40000 ALTER TABLE `citizens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `computers`
--

DROP TABLE IF EXISTS `computers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `computers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrtyd5pt4t79onlu9mxw72bjdq` (`account_id`),
  CONSTRAINT `FKrtyd5pt4t79onlu9mxw72bjdq` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `computers`
--

LOCK TABLES `computers` WRITE;
/*!40000 ALTER TABLE `computers` DISABLE KEYS */;
INSERT INTO `computers` VALUES (1,'Dell','7480ea30-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','system','Laptop VKS 001','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 21:37:54.304000','devTuan',1),(2,'HP','7482eba9-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 002','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',2),(3,'Dell','7482f3f6-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 003','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',3),(4,'Lenovo','7482f657-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 004','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',4),(5,'HP','7485134b-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 005','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',5),(6,'Dell','7485193d-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 006','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',6),(7,'Lenovo','74851b51-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 007','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',7),(8,'Asus','74851cb7-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 008','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',8),(9,'Acer','74851e07-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 009','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 22:01:31.215589','devTuan',1),(10,'Macbook','74851f49-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 010','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 22:01:31.215769','devTuan',1),(11,'MSI','74874204-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 011','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',11),(12,'HP','74874a4b-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 012','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',12),(13,'Dell','74874cba-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 013','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',13),(14,'Lenovo','74874ded-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 014','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 22:44:52.779897','devTuan',1),(15,'Asus','74874efd-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 015','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',15),(16,'Acer','74875004-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 016','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',16),(17,'Macbook','74875109-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 017','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',17),(18,'MSI','74875246-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 018','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',18),(19,'HP','74875356-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 019','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',19),(20,'Dell','7487545a-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 020','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',20),(21,'Lenovo','748a2015-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 021','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',21),(22,'Asus','748a25a6-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 022','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',22),(23,'Acer','748a27a6-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 023','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',23),(24,'Macbook','748a2909-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 024','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',24),(25,'MSI','748a308a-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 025','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',25),(26,'HP','748a326b-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 026','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',26),(27,'Dell','748a33b4-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 027','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',27),(28,'Lenovo','748a3503-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 028','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',28),(29,'Asus','748a3646-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 029','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',29),(30,'Acer','748a377e-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 030','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',30),(31,'Dell','748da33d-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 031','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',31),(32,'HP','748da8da-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 032','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',32),(33,'Lenovo','748daf8e-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 033','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',33),(34,'Apple','748db278-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 034','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',34),(35,'Asus','748db3ec-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 035','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',35),(36,'MSI','748db531-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 036','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',36),(37,'Samsung','748db683-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 037','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',37),(38,'Sony','748db7d4-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 038','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',38),(39,'Toshiba','748db955-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 039','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',39),(40,'Acer','748dbaa1-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 040','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',40),(41,'Dell','748dbbe4-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 041','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',41),(42,'HP','748dbd1f-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 042','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',42),(43,'Lenovo','748dbe54-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 043','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',43),(44,'Apple','748dc211-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 044','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',44),(45,'Asus','748dc38a-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 045','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',45),(46,'MSI','748dc4dd-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 046','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',46),(47,'Samsung','748dc61a-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 047','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',47),(48,'Sony','748dc755-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 048','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',48),(49,'Toshiba','748dc895-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 049','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',49),(50,'Acer','748dc9ce-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 050','Máy tính được cấp từ VKS','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',50),(51,'Acer','7491c133-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 051','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',51),(52,'Macbook','7491c6b2-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 052','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',52),(53,'MSI','7491d0f0-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 053','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',53),(54,'HP','7491d2bb-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 054','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',54),(55,'Dell','7491d3fd-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 055','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',55),(56,'Lenovo','7491d542-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 056','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 22:44:52.780059','devTuan',1),(57,'Asus','7491d688-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 057','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',57),(58,'Acer','7491d7cf-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 058','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',58),(59,'Macbook','7491d90a-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 059','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',59),(60,'MSI','7491da41-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 060','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',60),(61,'HP','7491dd8a-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 061','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',61),(62,'Dell','7491df40-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 062','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',62),(63,'Lenovo','7491e084-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 063','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',63),(64,'Asus','7491e1cf-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 064','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',64),(65,'Acer','7491e310-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 065','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',65),(66,'Macbook','7491e44a-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 066','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 22:46:20.343769','devTuan',1),(67,'MSI','7491e57c-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 067','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',67),(68,'HP','7491e6ba-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 068','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',68),(69,'Dell','7491e7ec-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 069','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',69),(70,'Lenovo','7491e923-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 070','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',70),(71,'Asus','7495a6f2-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 071','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',71),(72,'Acer','7495ae6e-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 072','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',72),(73,'Macbook','7495b0a8-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 073','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',73),(74,'MSI','7495b213-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 074','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',74),(75,'HP','7495b355-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 075','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',75),(76,'Dell','7495b48b-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 076','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',76),(77,'Lenovo','7495b5d2-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 077','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',77),(78,'Asus','7495b71d-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 078','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',78),(79,'Acer','7495b859-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 079','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',79),(80,'Macbook','7495b994-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 080','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',80),(81,'MSI','7495bacf-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 081','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',81),(82,'HP','7495bc0c-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 082','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',82),(83,'Dell','7495c0d1-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 083','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',83),(84,'Lenovo','7495c304-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 084','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',84),(85,'Asus','7495c90e-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 085','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',85),(86,'Dell','7499d461-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 086','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 11:27:30.382232','MCB002',85),(87,'HP','7499d9b0-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 087','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 22:47:33.085662','devTuan',1),(88,'Lenovo','7499db8f-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 088','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 22:47:33.085759','devTuan',1),(89,'Acer','7499dcd8-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 089','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 22:48:25.171942','devTuan',1),(90,'Asus','7499e42e-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 090','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 22:48:25.172086','devTuan',1),(91,'Dell','749a4c61-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 091','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-25 00:07:37.391906','devTuan',1),(92,'HP','749a4ed8-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 092','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-25 00:07:37.393943','devTuan',1),(93,'Lenovo','749a5047-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 093','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-25 00:09:13.496298','devTuan',1),(94,'Acer','749a5529-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 094','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-25 00:09:13.496488','devTuan',1),(95,'Asus','749a56c1-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 095','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 23:53:09.152890','devTuan',1),(96,'Dell','749a57e4-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 096','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-24 23:53:09.154136','devTuan',1),(97,'HP','749a5906-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 097','Máy tính xách tay được cấp','CONNECTED','LAPTOP_VKS','2024-09-25 00:09:13.496594','devTuan',1),(98,'Lenovo','749a5a26-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 098','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(99,'Acer','749a5b43-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 099','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(100,'Asus','749a5c69-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 100','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(101,'Dell','749a5d88-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 101','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(102,'HP','749a5ea0-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 102','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(103,'Lenovo','749a5fbc-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 103','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(104,'Acer','749a60d8-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 104','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(105,'Asus','749a61ef-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 105','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(106,'Dell','749a630d-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 106','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(107,'HP','749a6573-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 107','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(108,'Lenovo','749a67a3-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 108','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(109,'Acer','749a68cf-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 109','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(110,'Asus','749a69ed-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 110','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(111,'Dell','749a6b16-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 111','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(112,'HP','749a6c32-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 112','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(113,'Lenovo','749a70bc-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 113','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(114,'Acer','749a729d-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 114','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(115,'Asus','749a73cd-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 115','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(116,'Dell','749a74e8-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 116','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(117,'HP','749a7602-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 117','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(118,'Lenovo','749a7718-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 118','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(119,'Acer','749a7d05-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 119','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(120,'Asus','749a7ea1-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 120','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(121,'Dell','749fd46f-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 121','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(122,'HP','749fd9b4-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 122','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(123,'Lenovo','749fdb84-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 123','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(124,'Acer','749fdcbe-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 124','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(125,'Asus','749fddd8-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 125','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(126,'Dell','749fdeef-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 126','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(127,'HP','749fe42b-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 127','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(128,'Lenovo','749fe632-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 128','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(129,'Acer','749fe75b-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 129','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(130,'Asus','749fe86e-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 130','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(131,'Dell','749fe97f-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 131','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(132,'HP','749feb5f-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 132','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(133,'Lenovo','749fec8b-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 133','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(134,'Acer','749feda2-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 134','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(135,'Asus','749feee7-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 135','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(136,'Dell','74a0583b-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 136','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(137,'HP','74a05b3e-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 137','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(138,'Lenovo','74a05ca5-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 138','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(139,'Acer','74a05de0-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 139','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(140,'Asus','74a063df-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 140','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(141,'Dell','74a06511-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 141','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(142,'HP','74a0669a-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 142','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(143,'Lenovo','74a067cb-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 143','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(144,'Acer','74a068dd-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 144','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(145,'Asus','74a06cf9-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 145','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(146,'Dell','74a06ee5-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 146','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(147,'HP','74a0735a-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 147','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(148,'Lenovo','74a075a8-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 148','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(149,'Acer','74a076d9-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 149','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(150,'Asus','74a077e9-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 150','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(151,'Dell','74a078fd-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 151','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(152,'HP','74a07a08-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 152','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(153,'Lenovo','74a07b18-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 153','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(154,'Acer','74a07c22-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 154','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(155,'Asus','74a07ea2-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 155','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(156,'Dell','74a0807a-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 156','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(157,'HP','74a0819f-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 157','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(158,'Lenovo','74a082ac-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 158','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(159,'Acer','74a083ba-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 159','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(160,'Asus','74a084c6-79dc-11ef-884c-0242ac120002','2024-09-23 18:48:42.000000','MCB001','Laptop VKS 160','Máy tính xách tay được cấp','DISCONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','MCB001',NULL),(161,'Asus Rog','9AD532F5-E668-E841-9649-C4C2369D0C45','2024-09-23 18:48:42.000000','system','Asus Rog G531','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',86),(162,'Asus Vivo','A9AC285A-3C83-5246-A04A-BFC04F882BAB','2024-09-23 18:48:42.000000','system','Asus Vivo Book','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',88),(163,'Asus Vivo','5107B4F0-3B2F-5E43-BB03-A96E05F4E684','2024-09-23 18:48:42.000000','system','Asus Vivo HTTP','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',89),(164,'Iron man','8677726A-C0E0-90D8-907D-08BFB816303E','2024-09-23 18:48:42.000000','system','Siêu máy tính','PC','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',90),(165,'Mac','4C4C4544-0036-5310-804B-CAC04F4C3333','2024-09-23 18:48:42.000000','system','Mac Phế','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',93),(166,'Acer','EACBF3EB-EBA8-544C-93A2-568D01924488','2024-09-23 18:48:42.000000','system','Hieu PC','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',94),(167,'Dell','F63A7B76-3814-B443-A321-C01850FA232F','2024-09-23 18:48:42.000000','system','Manh LapTop','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',95),(168,'MSI','4C4C4544-0059-5110-804D-C3C04F305032','2024-09-23 18:48:42.000000','system','Thổ tả','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',96),(169,'Omen','4C4C4544-0051-4C10-8030-CAC04F524E32','2024-09-23 18:48:42.000000','system','THUYLINH','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',97),(170,'Asus','4C4C4544-004B-3510-8058-B5C04F594833','2024-09-23 18:48:42.000000','system','DESKTOP-VNOMJ5H - Giang','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',98),(171,'Dell','87BA8A1A-2483-EC11-80E3-088FC342EF9E','2024-09-23 18:48:42.000000','system','LAPTOP-UCUSAHC1 - Đạt','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',99),(172,'HP','57D2D381-651F-9845-8204-1DD004BC318F','2024-09-23 18:48:42.000000','system','ADMIN-PC - Hưng','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',100),(173,'Asus Rog','3C5C6B43-8D7C-9F46-9FA3-87E68C64F2ED','2024-09-23 18:48:42.000000','system','LAPTOP-L3S9BG7B - Đán','Máy tính xách tay cá nhân','CONNECTED','LAPTOP_VKS','2024-09-23 18:48:42.000000','system',101);
/*!40000 ALTER TABLE `computers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('1','admin','db/changelog/db.changelog-master.xml','2024-09-27 00:00:58',9,'RERAN','9:2d32b8622907d322313fd24e203e361e','sqlFile path=classpath:db/data/database_VKS.sql','',NULL,'4.27.0',NULL,NULL,'7370056204'),('2','admin','db/changelog/db.changelog-master.xml','2024-09-26 10:30:02',2,'EXECUTED','9:07b0793f00c0a301214518839906abbf','tagDatabase','','import-complete-v1','4.27.0',NULL,NULL,'7321400178'),('3','admin','db/changelog/db.changelog-master.xml','2024-09-26 10:30:02',3,'EXECUTED','9:01d352ad6dc48d7c131a745581533bf6','sql','',NULL,'4.27.0',NULL,NULL,'7321400178');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` tinyint NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,0,NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departments`
--

DROP TABLE IF EXISTS `departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments`
--

LOCK TABLES `departments` WRITE;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` VALUES (1,'PB_LANH_DAO','2024-09-23 18:48:40.000000','2024-09-23 18:48:40','Lãnh đạo','2024-09-23 18:48:40.000000','2024-09-23 18:48:40'),(2,'PB_KY_THUAT','2024-09-23 18:48:40.000000','2024-09-23 18:48:40','Kỹ thuật','2024-09-23 18:48:40.000000','2024-09-23 18:48:40'),(3,'PB_TRAT_TU_XA_HOI','2024-09-23 18:48:40.000000','2024-09-23 18:48:40','Trật tự xã hội','2024-09-23 18:48:40.000000','2024-09-23 18:48:40'),(4,'PB_AN_NINH_MA_TUY','2024-09-23 18:48:40.000000','2024-09-23 18:48:40','An ninh - Ma Túy','2024-09-23 18:48:40.000000','2024-09-23 18:48:40'),(5,'PB_KINH_TE_THAM_NHUNG','2024-09-23 18:48:40.000000','2024-09-23 18:48:40','Kinh tế - Tham nhũng','2024-09-23 18:48:40.000000','2024-09-23 18:48:40'),(6,'PB_DAN_SU_HANH_CHINH_KINH_DOANH','2024-09-23 18:48:40.000000','2024-09-23 18:48:40','Dân sự, hành chính, kinh doanh, thương mại','2024-09-23 18:48:40.000000','2024-09-23 18:48:40'),(7,'PB_KHIEU_NAI_TO_CAO','2024-09-23 18:48:40.000000','2024-09-23 18:48:40','Khiếu nại tố cáo','2024-09-23 18:48:40.000000','2024-09-23 18:48:40'),(8,'PB_TO_CHUC_CAN_BO','2024-09-23 18:48:40.000000','2024-09-23 18:48:40','Tổ chức cán bộ','2024-09-23 18:48:40.000000','2024-09-23 18:48:40'),(9,'PB_THANH_TRA_KHIEU_TO','2024-09-23 18:48:40.000000','2024-09-23 18:48:40','Thanh tra - khiếu tố','2024-09-23 18:48:40.000000','2024-09-23 18:48:40'),(10,'PB_THI_HANH_AN','2024-09-23 18:48:40.000000','2024-09-23 18:48:40','Thi hành án','2024-09-23 18:48:40.000000','2024-09-23 18:48:40');
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documents` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `case_id` bigint NOT NULL,
  `parent_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg5da0gvm8l4a5ryls4rq63mw5` (`case_id`),
  KEY `FKh6uwxbgs1faounqmgpphvubdr` (`parent_id`),
  CONSTRAINT `FKg5da0gvm8l4a5ryls4rq63mw5` FOREIGN KEY (`case_id`) REFERENCES `cases` (`id`),
  CONSTRAINT `FKh6uwxbgs1faounqmgpphvubdr` FOREIGN KEY (`parent_id`) REFERENCES `documents` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `histories`
--

DROP TABLE IF EXISTS `histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `histories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `impact` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `histories`
--

LOCK TABLES `histories` WRITE;
/*!40000 ALTER TABLE `histories` DISABLE KEYS */;
/*!40000 ALTER TABLE `histories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mindmap_template`
--

DROP TABLE IF EXISTS `mindmap_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mindmap_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `department_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjvgvv5gn0tfg9ud5n6uwwwx5u` (`department_id`),
  CONSTRAINT `FKjvgvv5gn0tfg9ud5n6uwwwx5u` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mindmap_template`
--

LOCK TABLES `mindmap_template` WRITE;
/*!40000 ALTER TABLE `mindmap_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `mindmap_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizations`
--

DROP TABLE IF EXISTS `organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `abbreviated_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `is_default` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizations`
--

LOCK TABLES `organizations` WRITE;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
INSERT INTO `organizations` VALUES (1,'VKSND','Nam Định','2024-09-23 18:48:40.000000','system',_binary '','Viện kiểm sát Nam Định','2024-09-23 18:48:40.000000','system');
/*!40000 ALTER TABLE `organizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profiles`
--

DROP TABLE IF EXISTS `profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profiles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(1000) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK16u75sdu73ex3c5se987o39la` (`account_id`),
  CONSTRAINT `FKp6tfqbfoowau3x81hki4t61dt` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profiles`
--

LOCK TABLES `profiles` WRITE;
/*!40000 ALTER TABLE `profiles` DISABLE KEYS */;
INSERT INTO `profiles` VALUES (1,NULL,'2024-09-23 18:48:40.000000','system','Nguyễn Trọng Long','Male','0987654321','2024-09-23 18:48:40.000000','system',1),(2,NULL,'2024-09-23 18:48:40.000000','1','Nguyễn Thiện Nhân','Male','0123456789','2024-09-23 18:48:40.000000','1',2),(3,NULL,'2024-09-23 18:48:40.000000','1','Nguyễn Đức Tài','Male','0123456798','2024-09-23 18:48:40.000000','1',3),(4,NULL,'2024-09-23 18:48:40.000000','1','Nguyễn Bảo Đức','Male','0123456879','2024-09-23 18:48:40.000000','1',4),(5,NULL,'2024-09-23 18:48:40.000000','1','Nguyễn Thiên Ân','Male','0953847621','2024-09-23 18:48:40.000000','1',5),(6,NULL,'2024-09-23 18:48:40.000000','1','Lê Phương Anh','Male','0864731952','2024-09-23 18:48:40.000000','1',6),(7,NULL,'2024-09-23 18:48:40.000000','1','Trần Minh Bảo','Male','0257946381','2024-09-23 18:48:40.000000','1',7),(8,NULL,'2024-09-23 18:48:40.000000','1','Hoàng Đức Cường','Male','0738961245','2024-09-23 18:48:40.000000','1',8),(9,NULL,'2024-09-23 18:48:40.000000','1','Phạm Hương Giang','Female','0629745813','2024-09-23 18:48:40.000000','1',9),(10,NULL,'2024-09-23 18:48:40.000000','1','Huỳnh Ngọc Hà','Male','0486975321','2024-09-23 18:48:40.000000','1',10),(11,NULL,'2024-09-23 18:48:40.000000','1','Nguyễn Trung Hiếu','Male','0791326548','2024-09-23 18:48:40.000000','1',11),(12,NULL,'2024-09-23 18:48:40.000000','1','Lê Gia Huy','Male','0365298174','2024-09-23 18:48:40.000000','1',12),(13,NULL,'2024-09-23 18:48:40.000000','1','Trần Bảo Khánh','Male','0412567893','2024-09-23 18:48:40.000000','1',13),(14,NULL,'2024-09-23 18:48:40.000000','1','Phạm Như Quỳnh','Female','0875649312','2024-09-23 18:48:40.000000','1',14),(15,NULL,'2024-09-23 18:48:40.000000','1','Hoàng Thanh Sơn','Male','0739286541','2024-09-23 18:48:40.000000','1',15),(16,NULL,'2024-09-23 18:48:40.000000','1','Nguyễn Gia Bảo','Male','0921435786','2024-09-23 18:48:40.000000','1',16),(17,NULL,'2024-09-23 18:48:40.000000','1','Lê Ngọc Bích','Female','0598437612','2024-09-23 18:48:40.000000','1',17),(18,NULL,'2024-09-23 18:48:40.000000','1','Trần Hồng Nhung','Female','0236971548','2024-09-23 18:48:40.000000','1',18),(19,NULL,'2024-09-23 18:48:40.000000','1','Phạm Minh Châu','Male','0817963542','2024-09-23 18:48:40.000000','1',19),(20,NULL,'2024-09-23 18:48:40.000000','1','Huỳnh Thiên Phúc','Male','0459782316','2024-09-23 18:48:40.000000','1',20),(21,NULL,'2024-09-23 18:48:40.000000','1','Nguyễn Duy Khánh','Male','0263785941','2024-09-23 18:48:40.000000','1',21),(22,NULL,'2024-09-23 18:48:40.000000','1','Lê Thùy Dương','Male','0734912865','2024-09-23 18:48:40.000000','1',22),(23,NULL,'2024-09-23 18:48:40.000000','1','Trần Anh Đức','Male','0578264913','2024-09-23 18:48:40.000000','1',23),(24,NULL,'2024-09-23 18:48:40.000000','1','Phạm Thái Dũng','Male','0391276845','2024-09-23 18:48:40.000000','1',24),(25,NULL,'2024-09-23 18:48:40.000000','1','Hoàng Văn Đạt','Male','0254639871','2024-09-23 18:48:40.000000','1',25),(26,NULL,'2024-09-23 18:48:41.000000','1','Nguyễn Hoàng Gia','Male','0716982354','2024-09-23 18:48:41.000000','1',26),(27,NULL,'2024-09-23 18:48:41.000000','1','Lê Minh Hiếu','Male','0832475691','2024-09-23 18:48:41.000000','1',27),(28,NULL,'2024-09-23 18:48:41.000000','1','Trần Ngọc Huyền','Male','0467931852','2024-09-23 18:48:41.000000','1',28),(29,NULL,'2024-09-23 18:48:41.000000','1','Phạm Quốc Huy','Male','0729164385','2024-09-23 18:48:41.000000','1',29),(30,NULL,'2024-09-23 18:48:41.000000','1','Huỳnh Bảo Linh','Male','0593268174','2024-09-23 18:48:41.000000','1',30),(31,NULL,'2024-09-23 18:48:41.000000','1','Nguyễn Mạnh Cường','Male','0851342967','2024-09-23 18:48:41.000000','1',31),(32,NULL,'2024-09-23 18:48:41.000000','1','Lê Yến Nhi','Male','0436915827','2024-09-23 18:48:41.000000','1',32),(33,NULL,'2024-09-23 18:48:41.000000','1','Trần Thanh Tùng','Male','0712586349','2024-09-23 18:48:41.000000','1',33),(34,NULL,'2024-09-23 18:48:41.000000','1','Phạm Quang Huy','Male','0987312654','2024-09-23 18:48:41.000000','1',34),(35,NULL,'2024-09-23 18:48:41.000000','1','Hoàng Đức Minh','Male','0264178935','2024-09-23 18:48:41.000000','1',35),(36,NULL,'2024-09-23 18:48:41.000000','1','Nguyễn Hồng Thủy','Male','0598752341','2024-09-23 18:48:41.000000','1',36),(37,NULL,'2024-09-23 18:48:41.000000','1','Lê Thị Hà','Male','0742891365','2024-09-23 18:48:41.000000','1',37),(38,NULL,'2024-09-23 18:48:41.000000','1','Trần Thái Dương','Male','0419635782','2024-09-23 18:48:41.000000','1',38),(39,NULL,'2024-09-23 18:48:41.000000','1','Phạm Quốc Khánh','Male','0286945731','2024-09-23 18:48:41.000000','1',39),(40,NULL,'2024-09-23 18:48:41.000000','1','Huỳnh Minh Anh','Male','0731526489','2024-09-23 18:48:41.000000','1',40),(41,NULL,'2024-09-23 18:48:41.000000','1','Nguyễn Đức Trí','Male','0952783614','2024-09-23 18:48:41.000000','1',41),(42,NULL,'2024-09-23 18:48:41.000000','1','Lê Hồng Hạnh','Male','0276341589','2024-09-23 18:48:41.000000','1',42),(43,NULL,'2024-09-23 18:48:41.000000','1','Trần Vĩnh Khang','Male','0617485932','2024-09-23 18:48:41.000000','1',43),(44,NULL,'2024-09-23 18:48:41.000000','1','Phạm Quốc Trung','Male','0893742165','2024-09-23 18:48:41.000000','1',44),(45,NULL,'2024-09-23 18:48:41.000000','1','Hoàng Thị Thanh','Male','0271963584','2024-09-23 18:48:41.000000','1',45),(46,NULL,'2024-09-23 18:48:41.000000','1','Nguyễn Bảo Ngọc','Male','0736149825','2024-09-23 18:48:41.000000','1',46),(47,NULL,'2024-09-23 18:48:41.000000','1','Lê Khắc Huy','Male','0519846273','2024-09-23 18:48:41.000000','1',47),(48,NULL,'2024-09-23 18:48:41.000000','1','Trần Đông Phương','Male','0372958164','2024-09-23 18:48:41.000000','1',48),(49,NULL,'2024-09-23 18:48:41.000000','1','Phạm Minh Hiếu','Male','0628917543','2024-09-23 18:48:41.000000','1',49),(50,NULL,'2024-09-23 18:48:41.000000','1','Huỳnh Bảo Trân','Male','0794163825','2024-09-23 18:48:41.000000','1',50),(51,NULL,'2024-09-23 18:48:41.000000','1','Nguyễn Quốc Đạt','Male','0281576943','2024-09-23 18:48:41.000000','1',51),(52,NULL,'2024-09-23 18:48:41.000000','1','Lê Thị Ngọc','Male','0439715862','2024-09-23 18:48:41.000000','1',52),(53,NULL,'2024-09-23 18:48:41.000000','1','Trần Ngọc Trinh','Male','0856243971','2024-09-23 18:48:41.000000','1',53),(54,NULL,'2024-09-23 18:48:41.000000','1','Phạm Đức Anh','Male','0279563418','2024-09-23 18:48:41.000000','1',54),(55,NULL,'2024-09-23 18:48:41.000000','1','Hoàng Thanh Hà','Male','0638451792','2024-09-23 18:48:41.000000','1',55),(56,NULL,'2024-09-23 18:48:41.000000','1','Nguyễn Việt Hưng','Male','0745961283','2024-09-23 18:48:41.000000','1',56),(57,NULL,'2024-09-23 18:48:41.000000','1','Lê Gia Bảo','Male','0392184675','2024-09-23 18:48:41.000000','1',57),(58,NULL,'2024-09-23 18:48:41.000000','1','Trần Phương Nhi','Male','0867512394','2024-09-23 18:48:41.000000','1',58),(59,NULL,'2024-09-23 18:48:41.000000','1','Phạm Minh Tuấn','Male','0984753621','2024-09-23 18:48:41.000000','1',59),(60,NULL,'2024-09-23 18:48:41.000000','1','Huỳnh Ngọc Linh','Male','0675218349','2024-09-23 18:48:41.000000','1',60),(61,NULL,'2024-09-23 18:48:41.000000','1','Nguyễn Đăng Khoa','Male','0389456712','2024-09-23 18:48:41.000000','1',61),(62,NULL,'2024-09-23 18:48:41.000000','1','Lê Minh Ngọc','Male','0542791836','2024-09-23 18:48:41.000000','1',62),(63,NULL,'2024-09-23 18:48:42.000000','1','Trần Quang Hải','Male','0713628495','2024-09-23 18:48:42.000000','1',63),(64,NULL,'2024-09-23 18:48:42.000000','1','Phạm Văn Tú','Male','0276948315','2024-09-23 18:48:42.000000','1',64),(65,NULL,'2024-09-23 18:48:42.000000','1','Hoàng Thị Uyên','Male','0859374612','2024-09-23 18:48:42.000000','1',65),(66,NULL,'2024-09-23 18:48:42.000000','1','Nguyễn Bảo Ngân','Male','0634127985','2024-09-23 18:48:42.000000','1',66),(67,NULL,'2024-09-23 18:48:42.000000','1','Lê Anh Tuấn','Male','0491563728','2024-09-23 18:48:42.000000','1',67),(68,NULL,'2024-09-23 18:48:42.000000','1','Trần Quốc Khánh','Male','0837214569','2024-09-23 18:48:42.000000','1',68),(69,NULL,'2024-09-23 18:48:42.000000','1','Phạm Đức Minh','Male','0562784139','2024-09-23 18:48:42.000000','1',69),(70,NULL,'2024-09-23 18:48:42.000000','1','Huỳnh Bích Thủy','Male','0418936257','2024-09-23 18:48:42.000000','1',70),(71,NULL,'2024-09-23 18:48:42.000000','1','Nguyễn Đình Đông','Male','0725139864','2024-09-23 18:48:42.000000','1',71),(72,NULL,'2024-09-23 18:48:42.000000','1','Lê Thị Hương','Male','0396758214','2024-09-23 18:48:42.000000','1',72),(73,NULL,'2024-09-23 18:48:42.000000','1','Trần Quang Vinh','Male','0871452936','2024-09-23 18:48:42.000000','1',73),(74,NULL,'2024-09-23 18:48:42.000000','1','Phạm Đăng Khôi','Male','0639214785','2024-09-23 18:48:42.000000','1',74),(75,NULL,'2024-09-23 18:48:42.000000','1','Hoàng Thị Mai','Male','0274981563','2024-09-23 18:48:42.000000','1',75),(76,NULL,'2024-09-23 18:48:42.000000','1','Nguyễn Bảo Trân','Male','0745612983','2024-09-23 18:48:42.000000','1',76),(77,NULL,'2024-09-23 18:48:42.000000','1','Lê Quốc Bảo','Male','0852693714','2024-09-23 18:48:42.000000','1',77),(78,NULL,'2024-09-23 18:48:42.000000','1','Trần Thị Linh','Male','0472186359','2024-09-23 18:48:42.000000','1',78),(79,NULL,'2024-09-23 18:48:42.000000','1','Phạm Mạnh Cường','Male','0931756824','2024-09-23 18:48:42.000000','1',79),(80,NULL,'2024-09-23 18:48:42.000000','1','Huỳnh Ngọc Minh','Male','0596328147','2024-09-23 18:48:42.000000','1',80),(81,NULL,'2024-09-23 18:48:42.000000','1','Nguyễn Đức Huy','Male','0768124935','2024-09-23 18:48:42.000000','1',81),(82,NULL,'2024-09-23 18:48:42.000000','1','Lê Thị Thúy','Male','0413952786','2024-09-23 18:48:42.000000','1',82),(83,NULL,'2024-09-23 18:48:42.000000','1','Trần Quang Khải','Male','0629354871','2024-09-23 18:48:42.000000','1',83),(84,NULL,'2024-09-23 18:48:42.000000','1','Phạm Đình Hiếu','Male','0785196423','2024-09-23 18:48:42.000000','1',84),(85,NULL,'2024-09-23 18:48:42.000000','1','Hoàng Thị Oanh','Male','0941326857','2024-09-23 18:48:42.000000','1',85),(86,NULL,'2024-09-23 18:48:42.000000','system','Vương Quốc Tuấn','Male','0979525395','2024-09-23 18:48:42.000000','system',86),(87,NULL,'2024-09-23 18:48:42.000000','system','Trần Hoàng Sơn','Male','0979525395','2024-09-23 18:48:42.000000','system',87),(88,NULL,'2024-09-23 18:48:42.000000','system','Phạm Hoàng Anh','Male','0979525395','2024-09-23 18:48:42.000000','system',88),(89,NULL,'2024-09-23 18:48:42.000000','system','Phạm Thị Thu Hà','Male','0979525395','2024-09-23 18:48:42.000000','system',89),(90,NULL,'2024-09-23 18:48:42.000000','system','Nguyễn Minh Đức','Male','0979525395','2024-09-23 18:48:42.000000','system',90),(92,NULL,'2024-09-23 18:48:42.000000','system','Đặng Ngọc Cường','Male','0979525395','2024-09-23 18:48:42.000000','system',92),(93,NULL,'2024-09-23 18:48:42.000000','system','Ngô Tài Vương','Male','0979525395','2024-09-23 18:48:42.000000','system',93),(94,NULL,'2024-09-23 18:48:42.000000','system','Lê Minh Hiếu','Male','0979525395','2024-09-23 18:48:42.000000','system',94),(95,NULL,'2024-09-23 18:48:42.000000','system','Nguyễn Tiến Mạnh','Male','0979525395','2024-09-23 18:48:42.000000','system',95),(96,NULL,'2024-09-23 18:48:42.000000','system','Lê Hoàng Cường','Male','0979525395','2024-09-23 18:48:42.000000','system',96),(97,NULL,'2024-09-23 18:48:42.000000','system','Nghiêm Xuân Thắng','Male','0979525395','2024-09-23 18:48:42.000000','system',97),(98,NULL,'2024-09-23 18:48:42.000000','system','Đặng Đình Giang','Male','0979525395','2024-09-23 18:48:42.000000','system',98),(99,NULL,'2024-09-23 18:48:42.000000','system','Phan Thành Đạt','Male','0979525395','2024-09-23 18:48:42.000000','system',99),(100,NULL,'2024-09-23 18:48:42.000000','system','Nguyễn Văn Hưng','Male','0979525395','2024-09-23 18:48:42.000000','system',100),(101,NULL,'2024-09-23 18:48:42.000000','system','Phạm Hoàng Anh','Male','0979525395','2024-09-23 18:48:42.000000','system',101);
/*!40000 ALTER TABLE `profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'VIEN_TRUONG','Viện trưởng'),(2,'VIEN_PHO','Viện phó'),(3,'IT_ADMIN','IT Admin'),(4,'TRUONG_PHONG','Trưởng phòng'),(5,'PHO_PHONG','Phó phòng'),(6,'KIEM_SAT_VIEN','Kiểm sát viên');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usbs`
--

DROP TABLE IF EXISTS `usbs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usbs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `key_usb` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `usb_code` varchar(255) DEFAULT NULL,
  `usb_vendor_code` varchar(255) DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK85rfkrltltw2eoele3xn729jr` (`account_id`),
  CONSTRAINT `FK85rfkrltltw2eoele3xn729jr` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usbs`
--

LOCK TABLES `usbs` WRITE;
/*!40000 ALTER TABLE `usbs` DISABLE KEYS */;
INSERT INTO `usbs` VALUES (7,'2024-09-20 03:07:24.000000','MCB001','75df56a5-76fd-11ef-884c-0242ac120002','Corsair','CONNECTED','2024-09-20 03:07:24.000000','MCB001','007','007',7),(8,'2024-09-20 03:07:24.000000','MCB001','75df5837-76fd-11ef-884c-0242ac120002','ADATA','CONNECTED','2024-09-20 03:07:24.000000','MCB001','008','008',8),(12,'2024-09-20 03:10:47.000000','MCB001','ee6a1647-76fd-11ef-884c-0242ac120002','Kingston','CONNECTED','2024-09-20 03:10:47.000000','MCB001','012','012',12),(13,'2024-09-20 03:10:47.000000','MCB001','ee6a1897-76fd-11ef-884c-0242ac120002','Transcend','CONNECTED','2024-09-20 03:10:47.000000','MCB001','013','013',13),(15,'2024-09-20 03:10:47.000000','MCB001','ee6a1e87-76fd-11ef-884c-0242ac120002','Sony','CONNECTED','2024-09-20 03:10:47.000000','MCB001','015','015',15),(17,'2024-09-20 03:10:47.000000','MCB001','ee6a217f-76fd-11ef-884c-0242ac120002','Corsair','CONNECTED','2024-09-20 03:10:47.000000','MCB001','017','017',17),(18,'2024-09-20 03:10:47.000000','MCB001','ee6a2702-76fd-11ef-884c-0242ac120002','ADATA','CONNECTED','2024-09-20 03:10:47.000000','MCB001','018','018',18),(19,'2024-09-20 03:10:47.000000','MCB001','ee6a2876-76fd-11ef-884c-0242ac120002','Patriot','CONNECTED','2024-09-20 03:10:47.000000','MCB001','019','019',19),(20,'2024-09-20 03:10:47.000000','MCB001','ee6a299d-76fd-11ef-884c-0242ac120002','Toshiba','CONNECTED','2024-09-20 03:10:47.000000','MCB001','020','020',20),(21,'2024-09-20 03:10:47.000000','MCB001','ee6a2af0-76fd-11ef-884c-0242ac120002','Lexar','CONNECTED','2024-09-20 03:10:47.000000','MCB001','021','021',21),(22,'2024-09-20 03:10:47.000000','MCB001','ee6a2c3a-76fd-11ef-884c-0242ac120002','Verbatim','CONNECTED','2024-09-20 03:10:47.000000','MCB001','022','022',22),(23,'2024-09-20 03:10:47.000000','MCB001','ee6a2d75-76fd-11ef-884c-0242ac120002','HP','CONNECTED','2024-09-20 03:10:47.000000','MCB001','023','023',23),(24,'2024-09-20 03:10:47.000000','MCB001','ee6a2ebc-76fd-11ef-884c-0242ac120002','Intenso','CONNECTED','2024-09-20 03:10:47.000000','MCB001','024','024',24),(25,'2024-09-20 03:10:47.000000','MCB001','ee6a2fe5-76fd-11ef-884c-0242ac120002','Integral','CONNECTED','2024-09-20 03:10:47.000000','MCB001','025','025',25),(26,'2024-09-20 03:10:47.000000','MCB001','ee6a310e-76fd-11ef-884c-0242ac120002','Emtec','CONNECTED','2024-09-20 03:10:47.000000','MCB001','026','026',26),(27,'2024-09-20 03:10:47.000000','MCB001','ee6a3233-76fd-11ef-884c-0242ac120002','Apacer','CONNECTED','2024-09-20 03:10:47.000000','MCB001','027','027',27),(28,'2024-09-20 03:10:47.000000','MCB001','ee6a3359-76fd-11ef-884c-0242ac120002','Buffalo','CONNECTED','2024-09-20 03:10:47.000000','MCB001','028','028',28),(29,'2024-09-20 03:10:47.000000','MCB001','ee6a3644-76fd-11ef-884c-0242ac120002','LaCie','CONNECTED','2024-09-20 03:10:47.000000','MCB001','029','029',29),(30,'2024-09-20 03:10:47.000000','MCB001','ee6a3838-76fd-11ef-884c-0242ac120002','Corsair','CONNECTED','2024-09-20 03:10:47.000000','MCB001','030','030',30),(31,'2024-09-20 03:10:47.000000','MCB001','ee6a3995-76fd-11ef-884c-0242ac120002','SanDisk','CONNECTED','2024-09-20 03:10:47.000000','MCB001','031','031',31),(32,'2024-09-20 03:10:47.000000','MCB001','ee6a3b10-76fd-11ef-884c-0242ac120002','Kingston','CONNECTED','2024-09-20 03:10:47.000000','MCB001','032','032',32),(33,'2024-09-20 03:10:47.000000','MCB001','ee6a3c4d-76fd-11ef-884c-0242ac120002','Transcend','CONNECTED','2024-09-20 03:10:47.000000','MCB001','033','033',33),(34,'2024-09-20 03:10:47.000000','MCB001','ee6a3d71-76fd-11ef-884c-0242ac120002','Samsung','CONNECTED','2024-09-20 03:10:47.000000','MCB001','034','034',34),(35,'2024-09-20 03:10:47.000000','MCB001','ee6a3ea1-76fd-11ef-884c-0242ac120002','Sony','CONNECTED','2024-09-20 03:10:47.000000','MCB001','035','035',35),(36,'2024-09-20 03:10:47.000000','MCB001','ee6a3fd9-76fd-11ef-884c-0242ac120002','PNY','CONNECTED','2024-09-20 03:10:47.000000','MCB001','036','036',36),(37,'2024-09-20 03:10:47.000000','MCB001','ee6a4115-76fd-11ef-884c-0242ac120002','Corsair','CONNECTED','2024-09-20 03:10:47.000000','MCB001','037','037',37),(38,'2024-09-20 03:10:47.000000','MCB001','ee6a423c-76fd-11ef-884c-0242ac120002','ADATA','CONNECTED','2024-09-20 03:10:47.000000','MCB001','038','038',38),(39,'2024-09-20 03:10:47.000000','MCB001','ee6a43b5-76fd-11ef-884c-0242ac120002','Patriot','CONNECTED','2024-09-20 03:10:47.000000','MCB001','039','039',39),(40,'2024-09-20 03:10:47.000000','MCB001','ee6a44ef-76fd-11ef-884c-0242ac120002','Toshiba','CONNECTED','2024-09-20 03:10:47.000000','MCB001','040','040',40),(41,'2024-09-20 03:10:47.000000','MCB001','ee6a461d-76fd-11ef-884c-0242ac120002','Lexar','CONNECTED','2024-09-20 03:10:47.000000','MCB001','041','041',41),(42,'2024-09-20 03:10:47.000000','MCB001','ee6a4749-76fd-11ef-884c-0242ac120002','Verbatim','CONNECTED','2024-09-20 03:10:47.000000','MCB001','042','042',42),(43,'2024-09-20 03:10:47.000000','MCB001','ee6a4c0b-76fd-11ef-884c-0242ac120002','HP','CONNECTED','2024-09-20 03:10:47.000000','MCB001','043','043',43),(44,'2024-09-20 03:10:47.000000','MCB001','ee6a4d6f-76fd-11ef-884c-0242ac120002','Intenso','CONNECTED','2024-09-20 03:10:47.000000','MCB001','044','044',44),(45,'2024-09-20 03:10:47.000000','MCB001','ee6a4ea5-76fd-11ef-884c-0242ac120002','Integral','CONNECTED','2024-09-20 03:10:47.000000','MCB001','045','045',45),(46,'2024-09-20 03:10:47.000000','MCB001','ee6a4fd1-76fd-11ef-884c-0242ac120002','Emtec','CONNECTED','2024-09-20 03:10:47.000000','MCB001','046','046',46),(47,'2024-09-20 03:10:47.000000','MCB001','ee6a50fa-76fd-11ef-884c-0242ac120002','Apacer','CONNECTED','2024-09-20 03:10:47.000000','MCB001','047','047',47),(48,'2024-09-20 03:10:47.000000','MCB001','ee6a5228-76fd-11ef-884c-0242ac120002','Buffalo','CONNECTED','2024-09-20 03:10:47.000000','MCB001','048','048',48),(49,'2024-09-20 03:10:47.000000','MCB001','ee6a534f-76fd-11ef-884c-0242ac120002','LaCie','CONNECTED','2024-09-20 03:10:47.000000','MCB001','049','049',49),(50,'2024-09-20 03:10:47.000000','MCB001','ee6a58c7-76fd-11ef-884c-0242ac120002','Corsair','CONNECTED','2024-09-20 03:10:47.000000','MCB001','050','050',50),(51,'2024-09-20 03:11:38.000000','MCB001','0cf3277a-76fe-11ef-884c-0242ac120002','SanDisk','CONNECTED','2024-09-20 03:11:38.000000','MCB001','051','051',51),(52,'2024-09-20 03:11:38.000000','MCB001','0cf3335e-76fe-11ef-884c-0242ac120002','Kingston','CONNECTED','2024-09-20 03:11:38.000000','MCB001','052','052',52),(53,'2024-09-20 03:11:38.000000','MCB001','0cf335bd-76fe-11ef-884c-0242ac120002','Transcend','CONNECTED','2024-09-20 03:11:38.000000','MCB001','053','053',53),(54,'2024-09-20 03:11:38.000000','MCB001','0cf33711-76fe-11ef-884c-0242ac120002','Samsung','CONNECTED','2024-09-20 03:11:38.000000','MCB001','054','054',54),(55,'2024-09-20 03:11:38.000000','MCB001','0cf33842-76fe-11ef-884c-0242ac120002','Sony','CONNECTED','2024-09-20 03:11:38.000000','MCB001','055','055',55),(57,'2024-09-20 03:11:38.000000','MCB001','0cf33a98-76fe-11ef-884c-0242ac120002','Corsair','CONNECTED','2024-09-20 03:11:38.000000','MCB001','057','057',57),(58,'2024-09-20 03:11:38.000000','MCB001','0cf33f78-76fe-11ef-884c-0242ac120002','ADATA','CONNECTED','2024-09-20 03:11:38.000000','MCB001','058','058',58),(59,'2024-09-20 03:11:38.000000','MCB001','0cf3432e-76fe-11ef-884c-0242ac120002','Patriot','CONNECTED','2024-09-20 03:11:38.000000','MCB001','059','059',59),(60,'2024-09-20 03:11:38.000000','MCB001','0cf34490-76fe-11ef-884c-0242ac120002','Toshiba','CONNECTED','2024-09-20 03:11:38.000000','MCB001','060','060',60),(61,'2024-09-20 03:11:38.000000','MCB001','0cf345e2-76fe-11ef-884c-0242ac120002','Lexar','CONNECTED','2024-09-20 03:11:38.000000','MCB001','061','061',61),(62,'2024-09-20 03:11:38.000000','MCB001','0cf34718-76fe-11ef-884c-0242ac120002','Verbatim','CONNECTED','2024-09-20 03:11:38.000000','MCB001','062','062',62),(63,'2024-09-20 03:11:38.000000','MCB001','0cf349ac-76fe-11ef-884c-0242ac120002','HP','CONNECTED','2024-09-20 03:11:38.000000','MCB001','063','063',63),(64,'2024-09-20 03:11:38.000000','MCB001','0cf34ad9-76fe-11ef-884c-0242ac120002','Intenso','CONNECTED','2024-09-20 03:11:38.000000','MCB001','064','064',64),(65,'2024-09-20 03:11:38.000000','MCB001','0cf34bfd-76fe-11ef-884c-0242ac120002','Integral','CONNECTED','2024-09-20 03:11:38.000000','MCB001','065','065',65),(67,'2024-09-20 03:11:38.000000','MCB001','0cf351a4-76fe-11ef-884c-0242ac120002','Apacer','CONNECTED','2024-09-20 03:11:38.000000','MCB001','067','067',67),(68,'2024-09-20 03:11:38.000000','MCB001','0cf354b3-76fe-11ef-884c-0242ac120002','Buffalo','CONNECTED','2024-09-20 03:11:38.000000','MCB001','068','068',68),(69,'2024-09-20 03:11:38.000000','MCB001','0cf35652-76fe-11ef-884c-0242ac120002','LaCie','CONNECTED','2024-09-20 03:11:38.000000','MCB001','069','069',69),(70,'2024-09-20 03:11:38.000000','MCB001','0cf35794-76fe-11ef-884c-0242ac120002','Corsair','CONNECTED','2024-09-20 03:11:38.000000','MCB001','070','070',70),(71,'2024-09-20 03:11:38.000000','MCB001','0cf358e1-76fe-11ef-884c-0242ac120002','SanDisk','CONNECTED','2024-09-20 03:11:38.000000','MCB001','071','071',71),(73,'2024-09-20 03:11:38.000000','MCB001','0cf35b38-76fe-11ef-884c-0242ac120002','Transcend','CONNECTED','2024-09-20 03:11:38.000000','MCB001','073','073',73),(74,'2024-09-20 03:11:38.000000','MCB001','0cf35c61-76fe-11ef-884c-0242ac120002','Samsung','CONNECTED','2024-09-20 03:11:38.000000','MCB001','074','074',74),(75,'2024-09-20 03:11:38.000000','MCB001','0cf35d8a-76fe-11ef-884c-0242ac120002','Sony','CONNECTED','2024-09-20 03:11:38.000000','MCB001','075','075',75),(76,'2024-09-20 03:11:38.000000','MCB001','0cf35f4b-76fe-11ef-884c-0242ac120002','PNY','CONNECTED','2024-09-20 03:11:38.000000','MCB001','076','076',76),(77,'2024-09-20 03:11:38.000000','MCB001','0cf36085-76fe-11ef-884c-0242ac120002','Corsair','CONNECTED','2024-09-20 03:11:38.000000','MCB001','077','077',77),(78,'2024-09-20 03:11:38.000000','MCB001','0cf361b0-76fe-11ef-884c-0242ac120002','ADATA','CONNECTED','2024-09-20 03:11:38.000000','MCB001','078','078',78),(79,'2024-09-20 03:11:38.000000','MCB001','0cf362de-76fe-11ef-884c-0242ac120002','Patriot','CONNECTED','2024-09-20 03:11:38.000000','MCB001','079','079',79),(80,'2024-09-20 03:11:38.000000','MCB001','0cf36403-76fe-11ef-884c-0242ac120002','Toshiba','CONNECTED','2024-09-20 03:11:38.000000','MCB001','080','080',80),(81,'2024-09-20 03:11:38.000000','MCB001','0cf36535-76fe-11ef-884c-0242ac120002','Lexar','CONNECTED','2024-09-20 03:11:38.000000','MCB001','081','081',81),(82,'2024-09-20 03:11:38.000000','MCB001','0cf36658-76fe-11ef-884c-0242ac120002','Verbatim','CONNECTED','2024-09-20 03:11:38.000000','MCB001','082','082',82),(83,'2024-09-20 03:11:38.000000','MCB001','0cf36b2d-76fe-11ef-884c-0242ac120002','HP','CONNECTED','2024-09-20 03:11:38.000000','MCB001','083','083',83),(86,'2024-09-20 03:24:41.000000','MCB001','dfa1d0c6-76ff-11ef-884c-0242ac120002','SanDisk','DISCONNECTED','2024-09-20 03:24:41.000000','MCB001','086','086',2),(87,'2024-09-20 03:24:41.000000','MCB001','dfa1dbd1-76ff-11ef-884c-0242ac120002','Kingston','DISCONNECTED','2024-09-20 03:24:41.000000','MCB001','087','087',3),(88,'2024-09-20 03:24:41.000000','MCB001','dfa1ddc0-76ff-11ef-884c-0242ac120002','Transcend','DISCONNECTED','2024-09-20 03:24:41.000000','MCB001','088','088',4),(89,'2024-09-20 03:24:41.000000','MCB001','dfa1df13-76ff-11ef-884c-0242ac120002','Samsung','DISCONNECTED','2024-09-20 03:24:41.000000','MCB001','089','089',5),(90,'2024-09-20 03:24:41.000000','MCB001','dfa1e02b-76ff-11ef-884c-0242ac120002','Sony','DISCONNECTED','2024-09-20 03:24:41.000000','MCB001','090','090',6),(92,'2024-09-20 03:26:53.000000','MCB001','2ebd442e-7700-11ef-884c-0242ac120002','Kingston','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','092','092',NULL),(93,'2024-09-20 03:26:53.000000','MCB001','2ebd485e-7700-11ef-884c-0242ac120002','Transcend','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','093','093',NULL),(94,'2024-09-20 03:26:53.000000','MCB001','2ebd49da-7700-11ef-884c-0242ac120002','Samsung','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','094','094',NULL),(95,'2024-09-20 03:26:53.000000','MCB001','2ebd4af8-7700-11ef-884c-0242ac120002','Sony','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','095','095',NULL),(96,'2024-09-20 03:26:53.000000','MCB001','2ebd4bfe-7700-11ef-884c-0242ac120002','PNY','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','096','096',NULL),(97,'2024-09-20 03:26:53.000000','MCB001','2ebd4fa7-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','097','097',NULL),(98,'2024-09-20 03:26:53.000000','MCB001','2ebd5158-7700-11ef-884c-0242ac120002','ADATA','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','098','098',NULL),(99,'2024-09-20 03:26:53.000000','MCB001','2ebd5271-7700-11ef-884c-0242ac120002','Patriot','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','099','099',NULL),(100,'2024-09-20 03:26:53.000000','MCB001','2ebd5379-7700-11ef-884c-0242ac120002','Toshiba','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','100','100',NULL),(101,'2024-09-20 03:26:53.000000','MCB001','2ebd54bd-7700-11ef-884c-0242ac120002','Lexar','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','101','101',NULL),(102,'2024-09-20 03:26:53.000000','MCB001','2ebd56ab-7700-11ef-884c-0242ac120002','Verbatim','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','102','102',NULL),(103,'2024-09-20 03:26:53.000000','MCB001','2ebd5d81-7700-11ef-884c-0242ac120002','HP','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','103','103',NULL),(104,'2024-09-20 03:26:53.000000','MCB001','2ebd5f00-7700-11ef-884c-0242ac120002','Intenso','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','104','104',NULL),(105,'2024-09-20 03:26:53.000000','MCB001','2ebd6011-7700-11ef-884c-0242ac120002','Integral','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','105','105',NULL),(106,'2024-09-20 03:26:53.000000','MCB001','2ebd6119-7700-11ef-884c-0242ac120002','Emtec','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','106','106',NULL),(107,'2024-09-20 03:26:53.000000','MCB001','2ebd622a-7700-11ef-884c-0242ac120002','Apacer','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','107','107',NULL),(108,'2024-09-20 03:26:53.000000','MCB001','2ebd63c9-7700-11ef-884c-0242ac120002','Buffalo','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','108','108',NULL),(109,'2024-09-20 03:26:53.000000','MCB001','2ebd64eb-7700-11ef-884c-0242ac120002','LaCie','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','109','109',NULL),(110,'2024-09-20 03:26:53.000000','MCB001','2ebd65f7-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','110','110',NULL),(111,'2024-09-20 03:26:53.000000','MCB001','2ebd66ff-7700-11ef-884c-0242ac120002','SanDisk','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','111','111',NULL),(112,'2024-09-20 03:26:53.000000','MCB001','2ebd6826-7700-11ef-884c-0242ac120002','Kingston','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','112','112',NULL),(113,'2024-09-20 03:26:53.000000','MCB001','2ebd693b-7700-11ef-884c-0242ac120002','Transcend','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','113','113',NULL),(114,'2024-09-20 03:26:53.000000','MCB001','2ebd6a4d-7700-11ef-884c-0242ac120002','Samsung','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','114','114',NULL),(115,'2024-09-20 03:26:53.000000','MCB001','2ebd6d77-7700-11ef-884c-0242ac120002','Sony','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','115','115',NULL),(116,'2024-09-20 03:26:53.000000','MCB001','2ebd6ec9-7700-11ef-884c-0242ac120002','PNY','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','116','116',NULL),(117,'2024-09-20 03:26:53.000000','MCB001','2ebd6fde-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','117','117',NULL),(118,'2024-09-20 03:26:53.000000','MCB001','2ebd70df-7700-11ef-884c-0242ac120002','ADATA','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','118','118',NULL),(119,'2024-09-20 03:26:53.000000','MCB001','2ebd71e5-7700-11ef-884c-0242ac120002','Patriot','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','119','119',NULL),(120,'2024-09-20 03:26:53.000000','MCB001','2ebd72e3-7700-11ef-884c-0242ac120002','Toshiba','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','120','120',NULL),(121,'2024-09-20 03:26:53.000000','MCB001','2ebd73e9-7700-11ef-884c-0242ac120002','Lexar','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','121','121',NULL),(122,'2024-09-20 03:26:53.000000','MCB001','2ebd74ea-7700-11ef-884c-0242ac120002','Verbatim','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','122','122',NULL),(123,'2024-09-20 03:26:53.000000','MCB001','2ebd75ec-7700-11ef-884c-0242ac120002','HP','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','123','123',NULL),(124,'2024-09-20 03:26:53.000000','MCB001','2ebd76f1-7700-11ef-884c-0242ac120002','Intenso','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','124','124',NULL),(125,'2024-09-20 03:26:53.000000','MCB001','2ebd77ec-7700-11ef-884c-0242ac120002','Integral','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','125','125',NULL),(126,'2024-09-20 03:26:53.000000','MCB001','2ebd7943-7700-11ef-884c-0242ac120002','Emtec','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','126','126',NULL),(127,'2024-09-20 03:26:53.000000','MCB001','2ebd7a54-7700-11ef-884c-0242ac120002','Apacer','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','127','127',NULL),(128,'2024-09-20 03:26:53.000000','MCB001','2ebd7b5f-7700-11ef-884c-0242ac120002','Buffalo','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','128','128',NULL),(129,'2024-09-20 03:26:53.000000','MCB001','2ebd7c66-7700-11ef-884c-0242ac120002','LaCie','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','129','129',NULL),(130,'2024-09-20 03:26:53.000000','MCB001','2ebd7d67-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','130','130',NULL),(131,'2024-09-20 03:26:53.000000','MCB001','2ebd8137-7700-11ef-884c-0242ac120002','SanDisk','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','131','131',NULL),(132,'2024-09-20 03:26:53.000000','MCB001','2ebdf9ea-7700-11ef-884c-0242ac120002','Kingston','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','132','132',NULL),(133,'2024-09-20 03:26:53.000000','MCB001','2ebdfc16-7700-11ef-884c-0242ac120002','Transcend','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','133','133',NULL),(134,'2024-09-20 03:26:53.000000','MCB001','2ebdfd65-7700-11ef-884c-0242ac120002','Samsung','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','134','134',NULL),(135,'2024-09-20 03:26:53.000000','MCB001','2ebdfe87-7700-11ef-884c-0242ac120002','Sony','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','135','135',NULL),(136,'2024-09-20 03:26:53.000000','MCB001','2ebdffa0-7700-11ef-884c-0242ac120002','PNY','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','136','136',NULL),(137,'2024-09-20 03:26:53.000000','MCB001','2ebe00a9-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','137','137',NULL),(138,'2024-09-20 03:26:53.000000','MCB001','2ebe01b3-7700-11ef-884c-0242ac120002','ADATA','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','138','138',NULL),(139,'2024-09-20 03:26:53.000000','MCB001','2ebe02c1-7700-11ef-884c-0242ac120002','Patriot','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','139','139',NULL),(140,'2024-09-20 03:26:53.000000','MCB001','2ebe03c4-7700-11ef-884c-0242ac120002','Toshiba','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','140','140',NULL),(141,'2024-09-20 03:26:53.000000','MCB001','2ebe04c9-7700-11ef-884c-0242ac120002','Lexar','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','141','141',NULL),(142,'2024-09-20 03:26:53.000000','MCB001','2ebe05d3-7700-11ef-884c-0242ac120002','Verbatim','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','142','142',NULL),(143,'2024-09-20 03:26:53.000000','MCB001','2ebe0a8f-7700-11ef-884c-0242ac120002','HP','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','143','143',NULL),(144,'2024-09-20 03:26:53.000000','MCB001','2ebe0ca3-7700-11ef-884c-0242ac120002','Intenso','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','144','144',NULL),(145,'2024-09-20 03:26:53.000000','MCB001','2ebe0dd0-7700-11ef-884c-0242ac120002','Integral','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','145','145',NULL),(146,'2024-09-20 03:26:53.000000','MCB001','2ebe0eef-7700-11ef-884c-0242ac120002','Emtec','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','146','146',NULL),(147,'2024-09-20 03:26:53.000000','MCB001','2ebe0fff-7700-11ef-884c-0242ac120002','Apacer','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','147','147',NULL),(148,'2024-09-20 03:26:53.000000','MCB001','2ebe110a-7700-11ef-884c-0242ac120002','Buffalo','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','148','148',NULL),(149,'2024-09-20 03:26:53.000000','MCB001','2ebe120d-7700-11ef-884c-0242ac120002','LaCie','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','149','149',NULL),(150,'2024-09-20 03:26:53.000000','MCB001','2ebe1704-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:26:53.000000','MCB001','150','150',NULL),(151,'2024-09-20 03:28:36.000000','MCB001','6c06268b-7700-11ef-884c-0242ac120002','SanDisk','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','151','151',NULL),(152,'2024-09-20 03:28:36.000000','MCB001','6c0636b8-7700-11ef-884c-0242ac120002','Kingston','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','152','152',NULL),(153,'2024-09-20 03:28:36.000000','MCB001','6c0638be-7700-11ef-884c-0242ac120002','Transcend','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','153','153',NULL),(154,'2024-09-20 03:28:36.000000','MCB001','6c063a1e-7700-11ef-884c-0242ac120002','Samsung','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','154','154',NULL),(155,'2024-09-20 03:28:36.000000','MCB001','6c063b45-7700-11ef-884c-0242ac120002','Sony','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','155','155',NULL),(156,'2024-09-20 03:28:36.000000','MCB001','6c063ee1-7700-11ef-884c-0242ac120002','PNY','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','156','156',NULL),(157,'2024-09-20 03:28:36.000000','MCB001','6c064013-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','157','157',NULL),(158,'2024-09-20 03:28:36.000000','MCB001','6c064124-7700-11ef-884c-0242ac120002','ADATA','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','158','158',NULL),(159,'2024-09-20 03:28:36.000000','MCB001','6c06422d-7700-11ef-884c-0242ac120002','Patriot','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','159','159',NULL),(160,'2024-09-20 03:28:36.000000','MCB001','6c064333-7700-11ef-884c-0242ac120002','Toshiba','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','160','160',NULL),(161,'2024-09-20 03:28:36.000000','MCB001','6c064437-7700-11ef-884c-0242ac120002','Lexar','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','161','161',NULL),(162,'2024-09-20 03:28:36.000000','MCB001','6c06455c-7700-11ef-884c-0242ac120002','Verbatim','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','162','162',NULL),(163,'2024-09-20 03:28:36.000000','MCB001','6c064675-7700-11ef-884c-0242ac120002','HP','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','163','163',NULL),(164,'2024-09-20 03:28:36.000000','MCB001','6c06477e-7700-11ef-884c-0242ac120002','Intenso','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','164','164',NULL),(165,'2024-09-20 03:28:36.000000','MCB001','6c064881-7700-11ef-884c-0242ac120002','Integral','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','165','165',NULL),(166,'2024-09-20 03:28:36.000000','MCB001','6c064989-7700-11ef-884c-0242ac120002','Emtec','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','166','166',NULL),(167,'2024-09-20 03:28:36.000000','MCB001','6c064a94-7700-11ef-884c-0242ac120002','Apacer','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','167','167',NULL),(168,'2024-09-20 03:28:36.000000','MCB001','6c064b9a-7700-11ef-884c-0242ac120002','Buffalo','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','168','168',NULL),(169,'2024-09-20 03:28:36.000000','MCB001','6c06516a-7700-11ef-884c-0242ac120002','LaCie','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','169','169',NULL),(170,'2024-09-20 03:28:36.000000','MCB001','6c06534c-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','170','170',NULL),(171,'2024-09-20 03:28:36.000000','MCB001','6c06546e-7700-11ef-884c-0242ac120002','SanDisk','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','171','171',NULL),(172,'2024-09-20 03:28:36.000000','MCB001','6c065599-7700-11ef-884c-0242ac120002','Kingston','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','172','172',NULL),(173,'2024-09-20 03:28:36.000000','MCB001','6c0656b0-7700-11ef-884c-0242ac120002','Transcend','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','173','173',NULL),(174,'2024-09-20 03:28:36.000000','MCB001','6c0657c1-7700-11ef-884c-0242ac120002','Samsung','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','174','174',NULL),(175,'2024-09-20 03:28:36.000000','MCB001','6c0658d1-7700-11ef-884c-0242ac120002','Sony','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','175','175',NULL),(176,'2024-09-20 03:28:36.000000','MCB001','6c065a3d-7700-11ef-884c-0242ac120002','PNY','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','176','176',NULL),(177,'2024-09-20 03:28:36.000000','MCB001','6c0660cf-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','177','177',NULL),(178,'2024-09-20 03:28:36.000000','MCB001','6c06644d-7700-11ef-884c-0242ac120002','ADATA','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','178','178',NULL),(179,'2024-09-20 03:28:36.000000','MCB001','6c0665fb-7700-11ef-884c-0242ac120002','Patriot','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','179','179',NULL),(180,'2024-09-20 03:28:36.000000','MCB001','6c06671a-7700-11ef-884c-0242ac120002','Toshiba','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','180','180',NULL),(181,'2024-09-20 03:28:36.000000','MCB001','6c066820-7700-11ef-884c-0242ac120002','Lexar','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','181','181',NULL),(182,'2024-09-20 03:28:36.000000','MCB001','6c066934-7700-11ef-884c-0242ac120002','Verbatim','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','182','182',NULL),(183,'2024-09-20 03:28:36.000000','MCB001','6c066a3d-7700-11ef-884c-0242ac120002','HP','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','183','183',NULL),(184,'2024-09-20 03:28:36.000000','MCB001','6c066b45-7700-11ef-884c-0242ac120002','Intenso','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','184','184',NULL),(185,'2024-09-20 03:28:36.000000','MCB001','6c066c4a-7700-11ef-884c-0242ac120002','Integral','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','185','185',NULL),(186,'2024-09-20 03:28:36.000000','MCB001','6c066d58-7700-11ef-884c-0242ac120002','Emtec','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','186','186',NULL),(187,'2024-09-20 03:28:36.000000','MCB001','6c066f00-7700-11ef-884c-0242ac120002','Apacer','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','187','187',NULL),(188,'2024-09-20 03:28:36.000000','MCB001','6c067023-7700-11ef-884c-0242ac120002','Buffalo','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','188','188',NULL),(189,'2024-09-20 03:28:36.000000','MCB001','6c06712d-7700-11ef-884c-0242ac120002','LaCie','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','189','189',NULL),(190,'2024-09-20 03:28:36.000000','MCB001','6c067238-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','190','190',NULL),(191,'2024-09-20 03:28:36.000000','MCB001','6c067342-7700-11ef-884c-0242ac120002','SanDisk','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','191','191',NULL),(192,'2024-09-20 03:28:36.000000','MCB001','6c06747b-7700-11ef-884c-0242ac120002','Kingston','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','192','192',NULL),(193,'2024-09-20 03:28:36.000000','MCB001','6c067595-7700-11ef-884c-0242ac120002','Transcend','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','193','193',NULL),(194,'2024-09-20 03:28:36.000000','MCB001','6c067a2f-7700-11ef-884c-0242ac120002','Samsung','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','194','194',NULL),(195,'2024-09-20 03:28:36.000000','MCB001','6c067b75-7700-11ef-884c-0242ac120002','Sony','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','195','195',NULL),(196,'2024-09-20 03:28:36.000000','MCB001','6c067c82-7700-11ef-884c-0242ac120002','PNY','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','196','196',NULL),(197,'2024-09-20 03:28:36.000000','MCB001','6c067d97-7700-11ef-884c-0242ac120002','Corsair','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','197','197',NULL),(198,'2024-09-20 03:28:36.000000','MCB001','6c067e9a-7700-11ef-884c-0242ac120002','ADATA','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','198','198',NULL),(199,'2024-09-20 03:28:36.000000','MCB001','6c067fa4-7700-11ef-884c-0242ac120002','Patriot','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','199','199',NULL),(200,'2024-09-20 03:28:36.000000','MCB001','6c06e51b-7700-11ef-884c-0242ac120002','Toshiba','DISCONNECTED','2024-09-20 03:28:36.000000','MCB001','200','200',NULL),(201,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston','CONNECTED','2024-09-20 03:28:36.000000','system','4660','43981',NULL),(202,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston','CONNECTED','2024-09-20 03:28:36.000000','system','5734','2385',86),(203,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston','CONNECTED','2024-09-20 03:28:36.000000','system','0325451064725767550','65535',88),(205,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston','CONNECTED','2024-09-20 03:28:36.000000','system','16896','5118',NULL),(206,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston','CONNECTED','2024-09-20 03:28:36.000000','system','4831981316105620527','13421',89),(207,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston','CONNECTED','2024-09-20 03:28:36.000000','system','8212761144516181559','13421',90),(208,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston - Vuong','CONNECTED','2024-09-20 03:28:36.000000','system','5064771027014607252','13421',93),(209,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston - Hieu','CONNECTED','2024-09-20 03:28:36.000000','system','9481591306742320908','13421',94),(210,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston - Manh','CONNECTED','2024-09-20 03:28:36.000000','system','0000000005♠↑','13421',95),(211,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston - Cuong','CONNECTED','2024-09-20 03:28:36.000000','system','1597741206447085225','13421',96),(212,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston - Thang','CONNECTED','2024-09-20 03:28:36.000000','system','4520701303212702566','13421',97),(213,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston - Giang','CONNECTED','2024-09-20 03:28:36.000000','system','5059491126801609348','13421',98),(214,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston - Dat','CONNECTED','2024-09-20 03:28:36.000000','system','0472931265725717657','13421',99),(215,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston - Hung','CONNECTED','2024-09-20 03:28:36.000000','system','8949531144082489153','13421',100),(216,'2024-09-20 03:28:36.000000','system','6c06e51b-7700-11ef-884c-0242ac120002','Kingston - Dan','CONNECTED','2024-09-20 03:28:36.000000','system','9827071110238195802','13421',101);
/*!40000 ALTER TABLE `usbs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-28 18:38:55
