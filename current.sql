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
  `title` char(200) NOT NULL,
  `body` longtext NOT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4;

/*Data for the table `article` */

insert  into `article`(`num`,`regDate`,`updateDate`,`memberNum`,`title`,`body`) values 
(1,'2021-02-16 19:15:28','2021-02-16 19:15:28',1,'제목1','내용1'),
(2,'2021-02-18 11:08:46','2021-02-18 15:02:20',1,'123213','223'),
(3,'2021-02-18 10:22:37','2021-02-18 10:22:41',1,'제목3','제목3'),
(4,'2021-02-18 13:54:23','2021-02-18 13:54:23',1,'fkfnf','34384'),
(5,'2021-02-18 14:17:32','2021-02-18 14:17:32',1,'2911','948'),
(6,'2021-02-18 14:43:19','2021-02-18 14:43:19',1,'1233','123123'),
(7,'2021-02-18 14:48:12','2021-02-18 14:48:12',1,'123321',''),
(8,'2021-02-18 14:53:02','2021-02-18 14:53:02',1,'12313','213213'),
(9,'2021-02-18 15:07:27','2021-02-18 15:07:27',1,'123123','245'),
(10,'2021-02-18 15:07:38','2021-02-18 15:07:38',1,'123123','245123'),
(11,'2021-02-18 15:07:41','2021-02-18 15:07:41',1,'123123','245123123'),
(12,'2021-02-18 15:07:44','2021-02-18 15:07:44',1,'123112323','245123'),
(13,'2021-02-18 15:07:47','2021-02-18 15:07:47',1,'123123','245123'),
(14,'2021-02-18 15:07:49','2021-02-18 15:07:49',1,'123123','245123'),
(15,'2021-02-18 15:07:50','2021-02-18 15:07:50',1,'123123','245123'),
(16,'2021-02-18 15:07:51','2021-02-18 15:07:51',1,'123123','245123'),
(17,'2021-02-18 15:07:52','2021-02-18 15:07:52',1,'123123','245123'),
(18,'2021-02-18 15:07:56','2021-02-18 15:07:56',1,'123123','24512365'),
(19,'2021-02-18 15:07:58','2021-02-18 15:07:58',1,'123123','245123'),
(20,'2021-02-18 15:08:01','2021-02-18 15:08:01',1,'123123','245123qe'),
(21,'2021-02-18 15:08:04','2021-02-18 15:08:04',1,'123123','2451230000'),
(22,'2021-02-18 15:08:31','2021-02-18 15:08:31',1,'123123','245123'),
(23,'2021-02-18 15:08:33','2021-02-18 15:08:33',1,'123123','245123'),
(24,'2021-02-18 15:08:34','2021-02-18 15:08:34',1,'123123','245123'),
(25,'2021-02-18 15:08:35','2021-02-18 15:08:35',1,'123123','245123'),
(26,'2021-02-18 15:08:36','2021-02-19 22:42:16',1,'123123','245123'),
(27,'2021-02-18 15:08:37','2021-02-18 15:08:37',1,'123123','245123'),
(28,'2021-02-18 15:08:38','2021-02-18 15:08:38',1,'123123','245123');

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `num` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `name` char(50) NOT NULL,
  `nickname` char(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `loginId` char(50) NOT NULL,
  `loginPw` varchar(200) NOT NULL,
  `adminLevel` tinyint(1) unsigned NOT NULL DEFAULT 2 COMMENT '0=탈퇴/1=로그인정지/2=일반/3=인증된,4=관리자',
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `member` */

insert  into `member`(`num`,`regDate`,`updateDate`,`name`,`nickname`,`email`,`loginId`,`loginPw`,`adminLevel`) values 
(1,'2021-02-19 22:35:19','2021-02-19 22:35:19','방혜성','banggu1997','banggu1997@gmail.com','test','1111',2),
(2,'2021-02-20 13:14:12','2021-02-20 13:14:12','방방','방혜성','bmg4211@naver.com','baobab612','1111',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
