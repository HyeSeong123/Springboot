/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 10.4.17-MariaDB : Database - lolHi
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`lolHi` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `lolHi`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `num` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `memberNum` int(10) unsigned NOT NULL,
  `boardNum` int(10) unsigned NOT NULL,
  `title` char(200) NOT NULL,
  `body` longtext NOT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4;

/*Data for the table `article` */

insert  into `article`(`num`,`regDate`,`updateDate`,`memberNum`,`boardNum`,`title`,`body`) values 
(1,'2021-02-16 19:15:28','2021-02-16 19:15:28',1,2,'제목1','내용1'),
(2,'2021-02-18 11:08:46','2021-02-18 15:02:20',1,2,'123213','223'),
(4,'2021-02-18 13:54:23','2021-02-18 13:54:23',1,2,'fkfnf','34384'),
(5,'2021-02-18 14:17:32','2021-02-18 14:17:32',1,2,'2911','948'),
(6,'2021-02-18 14:43:19','2021-02-18 14:43:19',1,2,'1233','123123'),
(7,'2021-02-18 14:48:12','2021-02-18 14:48:12',1,2,'123321',''),
(8,'2021-02-18 14:53:02','2021-02-18 14:53:02',1,2,'12313','213213'),
(9,'2021-02-18 15:07:27','2021-02-18 15:07:27',1,2,'123123','245'),
(10,'2021-02-18 15:07:38','2021-02-18 15:07:38',1,2,'123123','245123'),
(11,'2021-02-18 15:07:41','2021-02-18 15:07:41',1,2,'123123','245123123'),
(13,'2021-02-18 15:07:47','2021-02-18 15:07:47',1,2,'123123','245123'),
(14,'2021-02-18 15:07:49','2021-02-18 15:07:49',1,2,'123123','245123'),
(15,'2021-02-18 15:07:50','2021-02-18 15:07:50',1,2,'123123','245123'),
(16,'2021-02-18 15:07:51','2021-02-18 15:07:51',1,2,'123123','245123'),
(17,'2021-02-18 15:07:52','2021-02-18 15:07:52',1,2,'123123','245123'),
(18,'2021-02-18 15:07:56','2021-02-18 15:07:56',1,2,'123123','24512365'),
(19,'2021-02-18 15:07:58','2021-02-18 15:07:58',1,2,'123123','245123'),
(20,'2021-02-18 15:08:01','2021-02-18 15:08:01',1,2,'123123','245123qe'),
(21,'2021-02-18 15:08:04','2021-02-18 15:08:04',1,2,'123123','2451230000'),
(22,'2021-02-18 15:08:31','2021-02-18 15:08:31',1,2,'123123','245123'),
(23,'2021-02-18 15:08:33','2021-02-18 15:08:33',1,2,'123123','245123'),
(24,'2021-02-18 15:08:34','2021-02-18 15:08:34',1,2,'123123','245123'),
(25,'2021-02-18 15:08:35','2021-02-18 15:08:35',1,2,'123123','245123'),
(26,'2021-02-18 15:08:36','2021-02-19 22:42:16',1,2,'123123','245123'),
(27,'2021-02-18 15:08:37','2021-02-18 15:08:37',1,2,'123123','245123'),
(35,'2021-02-25 10:56:29','2021-03-02 17:16:02',2,2,'123214214','123213213'),
(36,'2021-03-02 15:38:48','2021-03-02 15:38:48',1,2,'새 게시글1','새 게시글1'),
(41,'2021-03-02 17:08:37','2021-03-02 17:15:44',2,2,'새 게시글2','새 게시글2');

/*Table structure for table `board` */

DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
  `num` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `name` char(50) NOT NULL,
  `code` char(50) NOT NULL,
  PRIMARY KEY (`num`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `board` */

insert  into `board`(`num`,`regDate`,`updateDate`,`name`,`code`) values 
(1,'2021-02-25 17:07:41','2021-02-25 17:07:41','공지사항','notice'),
(2,'2021-02-25 17:07:57','2021-02-25 17:07:57','자유게시판','free');

/*Table structure for table `like` */

DROP TABLE IF EXISTS `like`;

CREATE TABLE `like` (
  `num` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `memberNum` int(10) unsigned NOT NULL,
  `relNum` int(10) unsigned NOT NULL,
  `relTypeCode` char(30) NOT NULL,
  `point` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `like` */

insert  into `like`(`num`,`regDate`,`updateDate`,`memberNum`,`relNum`,`relTypeCode`,`point`) values 
(3,'2021-03-02 14:20:27','2021-03-02 14:20:27',1,35,'article',1),
(8,'2021-03-02 17:31:31','2021-03-02 17:31:31',2,41,'article',1);

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `num` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `name` char(50) NOT NULL,
  `nickname` char(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `hpNum` char(15) NOT NULL,
  `loginId` char(50) NOT NULL,
  `loginPw` varchar(200) NOT NULL,
  `adminLevel` tinyint(1) unsigned NOT NULL DEFAULT 2 COMMENT '0=탈퇴/1=로그인정지/2=일반/3=인증된,4=관리자',
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `member` */

insert  into `member`(`num`,`regDate`,`updateDate`,`name`,`nickname`,`email`,`hpNum`,`loginId`,`loginPw`,`adminLevel`) values 
(1,'2021-02-19 22:35:19','2021-03-02 15:47:07','가나다라11','banggu1997','banggu1997@gmail.com','010-2010-2020','test','11115',2),
(2,'2021-02-20 13:14:12','2021-03-02 16:14:50','방혜성','방혜성','bmg4211@naver.com','010-2010-2020','baobab612','1111',10),
(3,'2021-03-02 17:41:59','2021-03-02 17:41:59','방혜성','bababam','banggu1997@naver.com','010-2010-2020','test2','1111',2),
(4,'2021-03-02 21:09:37','2021-03-02 21:09:37','방방','방혜성','bmg4211@naver.com','010-8370-0420','banggu1997','1111',2);

/*Table structure for table `reply` */

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
  `num` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `memberNum` int(10) unsigned NOT NULL,
  `relNum` int(10) unsigned NOT NULL,
  `relTypeCode` char(30) NOT NULL,
  `body` longtext NOT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;

/*Data for the table `reply` */

insert  into `reply`(`num`,`regDate`,`updateDate`,`memberNum`,`relNum`,`relTypeCode`,`body`) values 
(1,'2021-02-23 09:19:23','2021-02-23 13:32:21',1,28,'article','65464633'),
(2,'2021-02-23 09:23:45','2021-02-23 13:32:01',1,28,'article','15673332'),
(8,'2021-02-23 10:52:35','2021-02-23 10:52:35',2,11,'article','ㅁㄴㅇ'),
(10,'2021-02-23 14:55:39','2021-02-23 14:55:39',1,6,'article','we32'),
(11,'2021-02-23 15:02:07','2021-02-23 15:02:07',1,6,'article','123'),
(13,'2021-02-23 15:03:44','2021-02-23 15:03:44',1,6,'article','6436'),
(14,'2021-02-23 15:05:20','2021-02-23 15:09:10',1,6,'article','1233'),
(15,'2021-02-23 16:10:57','2021-02-23 16:10:57',2,28,'article','123'),
(16,'2021-02-24 09:41:33','2021-02-24 09:41:33',2,28,'article','1314'),
(17,'2021-02-24 13:21:45','2021-02-24 13:21:45',1,26,'article','1115'),
(19,'2021-02-25 10:56:40','2021-02-25 10:56:40',2,34,'article','12232'),
(20,'2021-02-25 13:17:43','2021-02-25 13:17:43',1,35,'article','가나다라암나만마'),
(24,'2021-03-02 17:35:33','2021-03-02 17:35:48',2,36,'article','233'),
(25,'2021-03-02 17:43:46','2021-03-02 20:42:28',3,41,'article','2131');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
