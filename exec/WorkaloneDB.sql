CREATE DATABASE  IF NOT EXISTS `workalone` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `workalone`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: workalone
-- ------------------------------------------------------
-- Server version	9.1.0

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
-- Table structure for table `exercise`
--

DROP TABLE IF EXISTS `exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise` (
  `deleted` tinyint(1) DEFAULT '0',
  `exercise_repeat` int NOT NULL,
  `exercise_set` int NOT NULL,
  `rest_btw_set` int DEFAULT NULL,
  `seq` int NOT NULL,
  `group_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type_id` bigint DEFAULT NULL,
  `set_type` enum('COUNT','TIMER') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlmnspnhueql8py3ba68ajsrbl` (`group_id`),
  KEY `FK3p2so25iuwu4aa85tfe4mi9ua` (`type_id`),
  CONSTRAINT `FK3p2so25iuwu4aa85tfe4mi9ua` FOREIGN KEY (`type_id`) REFERENCES `exercise_type` (`id`),
  CONSTRAINT `FKlmnspnhueql8py3ba68ajsrbl` FOREIGN KEY (`group_id`) REFERENCES `exercise_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise`
--

LOCK TABLES `exercise` WRITE;
/*!40000 ALTER TABLE `exercise` DISABLE KEYS */;
INSERT INTO `exercise` VALUES (0,10,2,60,1,1,1,1,'COUNT'),(0,15,3,45,2,1,2,1,'COUNT'),(0,10,2,30,3,1,3,2,'COUNT'),(0,15,3,60,1,2,4,2,'COUNT'),(0,10,2,45,2,2,5,1,'COUNT'),(0,30,3,30,3,2,6,3,'TIMER'),(0,5,2,15,1,3,7,3,'TIMER'),(0,5,2,15,1,4,8,3,'TIMER'),(0,2,2,5,1,5,9,1,'COUNT'),(0,2,2,5,2,5,10,2,'COUNT'),(0,5,2,5,3,5,11,3,'TIMER');
/*!40000 ALTER TABLE `exercise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_group`
--

DROP TABLE IF EXISTS `exercise_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_group` (
  `rest_btw_exercise` int DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `organization_id` bigint DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtq44e2cfwyt6rh93oy1c0k43c` (`organization_id`),
  CONSTRAINT `FKtq44e2cfwyt6rh93oy1c0k43c` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_group`
--

LOCK TABLES `exercise_group` WRITE;
/*!40000 ALTER TABLE `exercise_group` DISABLE KEYS */;
INSERT INTO `exercise_group` VALUES (90,1,1,'통합형 운동1'),(90,2,1,'통합형 운동2'),(NULL,3,1,'개별형 운동'),(NULL,4,1,'플랭크 운동'),(15,5,1,'통합 운동');
/*!40000 ALTER TABLE `exercise_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_mapping`
--

DROP TABLE IF EXISTS `exercise_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_mapping` (
  `group_id` bigint NOT NULL,
  `member_id` bigint NOT NULL,
  PRIMARY KEY (`group_id`,`member_id`),
  KEY `FKae7rhxuuhf3sl33cddwpwr7uh` (`member_id`),
  CONSTRAINT `FKae7rhxuuhf3sl33cddwpwr7uh` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKif3wvdtlioiafikyosuk1k6bk` FOREIGN KEY (`group_id`) REFERENCES `exercise_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_mapping`
--

LOCK TABLES `exercise_mapping` WRITE;
/*!40000 ALTER TABLE `exercise_mapping` DISABLE KEYS */;
INSERT INTO `exercise_mapping` VALUES (1,1),(2,1),(3,1),(4,1),(5,1);
/*!40000 ALTER TABLE `exercise_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_summary`
--

DROP TABLE IF EXISTS `exercise_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_summary` (
  `date` date DEFAULT NULL,
  `kcal` int NOT NULL,
  `time` time(6) DEFAULT NULL,
  `exercise_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `video_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2i4fscsvagqamln5uagvyxmoq` (`exercise_id`),
  KEY `FKpd5b8klekmwyg8pesthnrrudk` (`member_id`),
  CONSTRAINT `FK2i4fscsvagqamln5uagvyxmoq` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`id`),
  CONSTRAINT `FKpd5b8klekmwyg8pesthnrrudk` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_summary`
--

LOCK TABLES `exercise_summary` WRITE;
/*!40000 ALTER TABLE `exercise_summary` DISABLE KEYS */;
INSERT INTO `exercise_summary` VALUES ('2024-11-01',150,'00:15:00.000000',1,1,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/e4f8a484-5800-421f-b37d-5e1b22fbda5f.mp4'),('2024-11-01',200,'00:20:00.000000',2,2,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/e4f8a484-5800-421f-b37d-5e1b22fbda5f.mp4'),('2024-11-01',250,'00:25:00.000000',3,3,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/e4f8a484-5800-421f-b37d-5e1b22fbda5f.mp4'),('2024-11-01',180,'00:18:00.000000',4,4,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/71e850b3-248a-4a49-b6b1-75e1e157e989.mp4'),('2024-11-01',220,'00:22:00.000000',5,5,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/71e850b3-248a-4a49-b6b1-75e1e157e989.mp4'),('2024-11-01',270,'00:27:00.000000',6,6,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/71e850b3-248a-4a49-b6b1-75e1e157e989.mp4'),('2024-11-01',120,'00:12:00.000000',7,7,1,NULL),('2024-11-02',150,'00:15:00.000000',1,8,1,NULL),('2024-11-02',200,'00:20:00.000000',2,9,1,NULL),('2024-11-02',250,'00:25:00.000000',3,10,1,NULL),('2024-11-02',120,'00:12:00.000000',7,11,1,NULL),('2024-11-04',180,'00:18:00.000000',4,12,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/055bf8cd-63f9-4d72-8715-9459087d110c.mp4'),('2024-11-04',220,'00:22:00.000000',5,13,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/055bf8cd-63f9-4d72-8715-9459087d110c.mp4'),('2024-11-04',270,'00:27:00.000000',6,14,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/055bf8cd-63f9-4d72-8715-9459087d110c.mp4'),('2024-11-06',120,'00:12:00.000000',7,15,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/055bf8cd-63f9-4d72-8715-9459087d110c.mp4'),('2024-11-08',180,'00:18:00.000000',4,16,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/055bf8cd-63f9-4d72-8715-9459087d110c.mp4'),('2024-11-08',220,'00:22:00.000000',5,17,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/055bf8cd-63f9-4d72-8715-9459087d110c.mp4'),('2024-11-08',270,'00:27:00.000000',6,18,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/055bf8cd-63f9-4d72-8715-9459087d110c.mp4'),('2024-11-13',150,'00:15:00.000000',1,19,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/e4f8a484-5800-421f-b37d-5e1b22fbda5f.mp4'),('2024-11-13',200,'00:20:00.000000',2,20,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/e4f8a484-5800-421f-b37d-5e1b22fbda5f.mp4'),('2024-11-13',250,'00:25:00.000000',3,21,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/e4f8a484-5800-421f-b37d-5e1b22fbda5f.mp4'),('2024-11-13',180,'00:18:00.000000',4,22,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/71e850b3-248a-4a49-b6b1-75e1e157e989.mp4'),('2024-11-13',220,'00:22:00.000000',5,23,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/71e850b3-248a-4a49-b6b1-75e1e157e989.mp4'),('2024-11-13',270,'00:27:00.000000',6,24,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/71e850b3-248a-4a49-b6b1-75e1e157e989.mp4'),('2024-11-13',120,'00:12:00.000000',7,25,1,'https://work-alone.s3.ap-northeast-2.amazonaws.com/video/055bf8cd-63f9-4d72-8715-9459087d110c.mp4'),('2024-11-16',150,'00:15:00.000000',1,26,1,NULL),('2024-11-16',200,'00:20:00.000000',2,27,1,NULL),('2024-11-16',250,'00:25:00.000000',3,28,1,NULL),('2024-11-16',120,'00:12:00.000000',7,29,1,NULL);
/*!40000 ALTER TABLE `exercise_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_type`
--

DROP TABLE IF EXISTS `exercise_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `basic_pose` varchar(255) DEFAULT NULL,
  `breath` varchar(255) DEFAULT NULL,
  `movement` varchar(255) DEFAULT NULL,
  `sub_title` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_type`
--

LOCK TABLES `exercise_type` WRITE;
/*!40000 ALTER TABLE `exercise_type` DISABLE KEYS */;
INSERT INTO `exercise_type` VALUES (1,'1. 발을 어깨너비로 벌리고, 발끝은 약간 바깥쪽을 향하게 합니다.\n2. 가슴은 펴고, 시선은 정면을 유지하며, 허리를 곧게 펴줍니다.','내려갈 때 숨을 들이마시고, 일어설 때 숨을 내쉽니다.','1. 무릎을 구부리며 엉덩이를 뒤로 빼면서 천천히 앉습니다.\n2. 허벅지가 바닥과 평행할 때까지 내려가며, 무릎이 발끝을 넘지 않도록 주의합니다.\n3. 내려갔다가 다시 천천히 일어나면서 원래 자세로 돌아옵니다.','허벅지와 코어 근력 강화','스쿼트'),(2,'1. 손은 어깨 너비로 벌리고 바닥을 짚습니다.\n2. 몸을 곧게 펴고, 팔과 어깨, 엉덩이가 일직선을 이루도록 합니다.','내려갈 때 숨을 들이마시고, 올라올 때 숨을 내쉽니다.','1. 팔꿈치를 구부리며 몸을 천천히 내려줍니다.\n2. 가슴이 바닥에 가까워질 때까지 내려가고, 팔로 밀어 원래 자세로 돌아옵니다.','상체와 코어 근력 강화','푸쉬업'),(3,'1. 팔꿈치를 어깨 아래에 두고, 몸을 곧게 편 상태로 바닥을 짚습니다.\n2. 어깨, 엉덩이, 발목이 일직선을 이루도록 유지합니다.','코어에 힘을 주면서 편안하게 호흡을 유지합니다.','1. 팔꿈치와 발로 체중을 지탱하며, 자세를 최대한 유지합니다.','코어 안정성 및 체력 강화','플랭크'),(4,'1. 무릎을 구부리고 발바닥을 바닥에 붙인 채로 누워 시작합니다.\n2. 손은 가볍게 머리 뒤에 둡니다.','일어날 때 숨을 내쉬고, 내려갈 때 숨을 들이마십니다.','1. 복근에 힘을 주면서 상체를 들어 올려 무릎 쪽으로 향합니다.\n2. 천천히 내려가면서 원래 자세로 돌아옵니다.','복근 강화 운동','윗몸 일으키기');
/*!40000 ALTER TABLE `exercise_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `gender` bit(1) DEFAULT NULL,
  `height` int NOT NULL,
  `is_recording` bit(1) DEFAULT NULL,
  `is_trainer` bit(1) DEFAULT NULL,
  `weight` int NOT NULL,
  `member_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (_binary '',178,_binary '\0',_binary '\0',70,1,'minsoo@example.com','김민수','minsoo','010-1234-5678'),(_binary '\0',165,_binary '',_binary '',55,2,'seoyun@example.com','이서윤','seoyun','010-2345-6789'),(_binary '',175,_binary '',_binary '\0',68,3,'jihun@example.com','박지훈','jihun','010-3456-7890'),(_binary '\0',160,_binary '\0',_binary '\0',52,4,'soojin@example.com','최수진','soojin','010-4567-8901'),(_binary '',180,_binary '',_binary '',77,5,'hyunwoo@example.com','정현우','hyunwoo','010-5678-9012'),(_binary '\0',168,_binary '\0',_binary '\0',58,6,'jimin@example.com','한지민','jimin','010-6789-0123'),(_binary '',182,_binary '\0',_binary '',75,7,'junseo@example.com','송준서','junseo','010-7890-1234'),(_binary '\0',163,_binary '',_binary '',50,8,'areum@example.com','윤아름','areum','010-8901-2345'),(_binary '',173,_binary '\0',_binary '\0',65,9,'doyun@example.com','김도윤','doyun','010-9012-3456'),(_binary '\0',158,_binary '',_binary '\0',53,10,'jia@example.com','이지아','jia','010-0123-4567'),(NULL,0,NULL,NULL,83,11,NULL,'최광림',NULL,NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` VALUES (1,'헬스 클럽 A'),(2,'헬스 클럽 B'),(3,'헬스 클럽 C'),(4,'역삼 청소년 센터');
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_mapping`
--

DROP TABLE IF EXISTS `organization_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization_mapping` (
  `member_id` bigint NOT NULL,
  `organization_id` bigint NOT NULL,
  PRIMARY KEY (`member_id`,`organization_id`),
  KEY `FKrrnle38j5tgwbs01urtwne8j9` (`organization_id`),
  CONSTRAINT `FKboyvin85gbs65o7eov6grpyib` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKrrnle38j5tgwbs01urtwne8j9` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_mapping`
--

LOCK TABLES `organization_mapping` WRITE;
/*!40000 ALTER TABLE `organization_mapping` DISABLE KEYS */;
INSERT INTO `organization_mapping` VALUES (1,1),(2,1),(3,1),(4,1),(2,4);
/*!40000 ALTER TABLE `organization_mapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-18 16:10:34
