SELECT * FROM `member` WHERE memberNum = '1'

SELECT * FROM `like` WHERE memberNum = '1'


/* 롤 하이 */

DROP DATABASE IF EXISTS lolHi;
CREATE DATABASE lolHi;
USE lolHi;

ALTER TABLE `like` AUTO_INCREMENT= 0 ;

CREATE TABLE article(
    num INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,   
    title CHAR(200) NOT NULL,
    `body` LONGTEXT NOT NULL
);

ALTER TABLE article ADD COLUMN memberNum INT(10) UNSIGNED NOT NULL AFTER updateDate

UPDATE article SET memberNum = 1 WHERE memberNum = 0;

DESC article;
SELECT * FROM article;

INSERT INTO article
SET regDate= NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1'

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

# 멤버 번호 재지정
ALTER TABLE `member` AUTO_INCREMENT = 2;

SELECT A.*, M.name AS extra__writer
FROM article AS A
JOIN `member` AS M
ON A.memberNum = M.num
ORDER BY A.num DESC

SELECT A.*
		FROM article AS A
		WHERE num = 29