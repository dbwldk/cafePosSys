CREATE DATABASE  IF NOT EXISTS `cafe_pos` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cafe_pos`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: cafe_pos
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `ing_id` varchar(50) NOT NULL,
  `ing_name` varchar(50) NOT NULL,
  `EXP` date NOT NULL,
  PRIMARY KEY (`ing_id`),
  UNIQUE KEY `ing_name_UNIQUE` (`ing_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES ('BEAN','원두','2022-12-23'),('CIDE','사이다','2023-02-02'),('C_BR','원액','2022-12-22'),('DOUGH','생지','2023-01-01'),('EGG','에그샐러드','2022-12-15'),('KIWI','키위','2022-12-22'),('LEMON','레몬청','2022-12-22'),('MANGO','망고','2022-12-23'),('MILK','우유','2022-12-15'),('SAND','샌드위치 빵','2022-12-23'),('ST_BER','딸기','2022-12-15');
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `ing_id` varchar(50) NOT NULL,
  `cnt` int NOT NULL,
  PRIMARY KEY (`ing_id`),
  CONSTRAINT `FK_INV_INGID` FOREIGN KEY (`ing_id`) REFERENCES `ingredient` (`ing_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES ('BEAN',3),('C_BR',3),('DOUGH',2),('EGG',3),('KIWI',3),('LEMON',3),('MANGO',3),('MILK',3),('SAND',3),('ST_BER',3);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `menu_id` varchar(50) NOT NULL,
  `menu_name` varchar(50) NOT NULL,
  `type_id` int NOT NULL,
  `sold_out` tinyint NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `menu_name_UNIQUE` (`menu_name`),
  KEY `FK_MENU_TYPE` (`type_id`),
  CONSTRAINT `FK_MENU_TYPE` FOREIGN KEY (`type_id`) REFERENCES `menu_type` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('AD_LE','레모네이드',2,0,2500),('AD_MA','망고에이드',2,0,2500),('AME_H','핫 아메리카노',1,0,1900),('AME_I','아이스 아메리카노',1,0,1900),('B_KIW','키위 쥬스',3,0,4000),('B_MILK','우유',3,0,1500),('CROF','크로플',4,0,1700),('C_BR','콜드브루',1,0,3000),('SM_ST','딸기 스무디',2,0,2500),('SW_EG','에그 샌드위치',4,0,3000),('SW_ST','딸기 샌드위치',4,0,3000);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_type`
--

DROP TABLE IF EXISTS `menu_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_type` (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) NOT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `type_name_UNIQUE` (`type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_type`
--

LOCK TABLES `menu_type` WRITE;
/*!40000 ALTER TABLE `menu_type` DISABLE KEYS */;
INSERT INTO `menu_type` VALUES (2,'논커피'),(4,'베이커리'),(3,'병음료'),(1,'커피');
/*!40000 ALTER TABLE `menu_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `order_id` varchar(50) NOT NULL,
  `order_time` datetime NOT NULL,
  `ing_id` varchar(50) NOT NULL,
  `cnt` int NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK_ORD_INGID_idx` (`ing_id`),
  CONSTRAINT `FK_ORD_INGID` FOREIGN KEY (`ing_id`) REFERENCES `ingredient` (`ing_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('20221223_BEAN','2022-12-23 23:59:03','BEAN',2),('20230101_DOUGH','2023-01-01 12:32:23','DOUGH',2);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe` (
  `recipe_id` int NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(50) NOT NULL,
  `ing_id` varchar(50) NOT NULL,
  PRIMARY KEY (`recipe_id`),
  KEY `FK_REC_MENUID_idx` (`menu_id`),
  KEY `FK_REC_INGID_idx` (`ing_id`),
  CONSTRAINT `FK_REC_INGID` FOREIGN KEY (`ing_id`) REFERENCES `ingredient` (`ing_id`),
  CONSTRAINT `FK_REC_MENUID` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT INTO `recipe` VALUES (1,'CROF','DOUGH'),(2,'AME_H','BEAN'),(3,'AME_I','BEAN'),(4,'AD_LE','LEMON'),(5,'AD_LE','CIDE'),(6,'AD_MA','MANGO'),(7,'AD_MA','CIDE'),(8,'SM_ST','MILK'),(9,'SM_ST','ST_BER'),(10,'SW_EG','EGG'),(11,'SW_EG','SAND'),(12,'SW_ST','ST_BER'),(13,'SW_ST','SAND');
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sell`
--

DROP TABLE IF EXISTS `sell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sell` (
  `sell_id` varchar(50) NOT NULL,
  `sell_time` datetime NOT NULL,
  `menu_id` varchar(50) NOT NULL,
  `menu_cnt` int NOT NULL,
  `total` int NOT NULL,
  PRIMARY KEY (`sell_id`),
  KEY `FK_SEL_MENUID_idx` (`menu_id`),
  CONSTRAINT `FK_SEL_MENUID` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sell`
--

LOCK TABLES `sell` WRITE;
/*!40000 ALTER TABLE `sell` DISABLE KEYS */;
INSERT INTO `sell` VALUES ('20221202_233232_AME_H','2022-12-02 23:32:32','AME_H',2,3000),('20221202_233232_CROF','2022-12-02 23:32:32','CROF',1,3000);
/*!40000 ALTER TABLE `sell` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-11 16:06:20
