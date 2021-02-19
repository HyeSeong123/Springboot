ALTER TABLE `like` AUTO_INCREMENT= 0 ;
SELECT * FROM `member` WHERE memberNum = '1'

SELECT * FROM `like` WHERE memberNum = '1'


/* 롤 하이 */

DROP DATABASE IF EXISTS lolHi;
CREATE DATABASE lolHi;
USE lolHi;

CREATE TABLE article(
    num INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(200) NOT NULL,
    `body` LONGTEXT NOT NULL
);

INSERT INTO article
SET regDate= NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1'

ALTER TABLE article AUTO_INCREMENT= 4 ;

SELECT COUNT(*)
FROM article

CREATE TABLE `member`(
    num INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(50) NOT NULL,
    `nickname` CHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    loginId CHAR(50) NOT NULL,
    loginPw VARCHAR(200) NOT NULL,
    adminLevel TINYINT(1) UNSIGNED NOT NULL DEFAULT 2 COMMENT '0=탈퇴/1=로그인정지/2=일반/3=인증된,4=관리자'    
);