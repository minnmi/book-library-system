-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.24-MariaDB

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
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Unique autoincrementing identifier.',
  `name` varchar(255) NOT NULL COMMENT 'Name of author.',
  `created_at` datetime NOT NULL DEFAULT current_timestamp() COMMENT 'Date when the record was created.',
  `deleted_at` datetime DEFAULT NULL COMMENT 'Date when the record was deleted.',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='Tabela que coleciona autores';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (5,'Conrado Silva Jorge','2022-09-20 16:24:37',NULL),(8,'Conrado','2022-11-05 16:43:26',NULL),(9,'Leandro','2022-11-06 19:35:09',NULL),(10,'Reis','2022-11-06 19:37:53',NULL),(11,'Matheus','2022-11-06 21:47:02',NULL),(12,'Matheus','2022-11-06 22:07:43',NULL),(13,'Matheus Candido Teixeira','2022-11-07 01:03:06',NULL),(14,'Livia','2022-11-07 01:12:04',NULL);
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Unique autoincrementing identifier.',
  `name` varchar(255) NOT NULL COMMENT 'Name of book.',
  `isbn` varchar(255) NOT NULL,
  `publisher_id` int(11) NOT NULL COMMENT 'Publisher identifier.',
  `literature_category_id` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp() COMMENT 'Date when the record was created.',
  `deleted_at` datetime DEFAULT NULL COMMENT 'Date when the record was deleted.',
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_book_publisher` (`publisher_id`),
  KEY `FK_book_literature_category` (`literature_category_id`),
  CONSTRAINT `FK4hw13bw7b1qsc5hx89syfcmfe` FOREIGN KEY (`literature_category_id`) REFERENCES `literature_category` (`id`),
  CONSTRAINT `FK_book_literature_category` FOREIGN KEY (`literature_category_id`) REFERENCES `literature_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_book_publisher` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKgtvt7p649s4x80y6f4842pnfq` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='Tabela que coleciona títulos das obras';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (10,'Matheus chato','465464',2,6,'2022-11-06 22:10:19',NULL,1),(11,'Matheus s2','465464',2,6,'2022-11-07 00:22:40',NULL,1),(15,'Matheus s2','465464',2,6,'2022-11-07 00:37:53',NULL,1),(25,'Matheusssss','465464',2,6,'2022-11-07 00:51:08',NULL,1),(26,'Matheusssss','465464',2,6,'2022-11-07 00:57:19',NULL,1),(27,'Matheusssss','465464',2,6,'2022-11-07 01:03:21',NULL,1);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_authorship`
--

DROP TABLE IF EXISTS `book_authorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_authorship` (
  `book_id` bigint(20) NOT NULL,
  `author_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_authorship`
--

LOCK TABLES `book_authorship` WRITE;
/*!40000 ALTER TABLE `book_authorship` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_authorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `book_id` int(11) NOT NULL,
  `current_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `priority` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_booking` (`user_id`),
  KEY `fk_book_booking` (`book_id`),
  CONSTRAINT `fk_book_booking` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_booking` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `literature_category`
--

DROP TABLE IF EXISTS `literature_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `literature_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp() COMMENT 'Date when the record was created.',
  `deleted_at` datetime DEFAULT NULL COMMENT 'Date when the record was deleted.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='Tabela que coleciona o gênero da obra';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literature_category`
--

LOCK TABLES `literature_category` WRITE;
/*!40000 ALTER TABLE `literature_category` DISABLE KEYS */;
INSERT INTO `literature_category` VALUES (5,'Romance','2022-11-05 20:17:40',NULL),(6,'Comédia','2022-11-06 12:06:07',NULL);
/*!40000 ALTER TABLE `literature_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loaned`
--

DROP TABLE IF EXISTS `loaned`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loaned` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `final_date` datetime DEFAULT NULL,
  `initial_date` datetime DEFAULT NULL,
  `book_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `returned_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `returned` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhw7h3ke82arwiwutljpvm6tbo` (`user_id`),
  CONSTRAINT `FKhw7h3ke82arwiwutljpvm6tbo` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loaned`
--

LOCK TABLES `loaned` WRITE;
/*!40000 ALTER TABLE `loaned` DISABLE KEYS */;
/*!40000 ALTER TABLE `loaned` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Unique autoincrementing identifier.',
  `name` varchar(255) NOT NULL COMMENT 'Name of book publisher.',
  `created_at` datetime NOT NULL DEFAULT current_timestamp() COMMENT 'Date when the record was created.',
  `deleted_at` datetime DEFAULT NULL COMMENT 'Date when the record was deleted.',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='Tabela que coleciona editoras';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` VALUES (2,'HarperCollins','2022-08-18 11:27:00',NULL),(3,'Nova Fronteira','2022-08-18 11:27:00',NULL),(4,'Antofagica','2022-08-18 11:27:00',NULL),(5,'Alta Books','2022-08-18 11:27:00',NULL),(6,'Nova','2022-10-18 20:44:45',NULL),(7,'Nova','2022-10-25 17:09:22',NULL),(8,'Nova','2022-11-05 16:32:59',NULL);
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'library'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-24 14:48:00
