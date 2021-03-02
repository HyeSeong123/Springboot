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
ALTER TABLE article ADD COLUMN boardNum INT(10) UNSIGNED NOT NULL AFTER memberNum

UPDATE article SET boardNum = 1 WHERE num <=15;
UPDATE article SET boardNum = 2 WHERE num >15;

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

ALTER TABLE `member` ADD COLUMN hpNum CHAR(15) NOT NULL AFTER email

# 멤버 번호 재지정
ALTER TABLE `member` AUTO_INCREMENT = 2;

DROP TABLE reply
CREATE TABLE reply(
    num INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberNum INT(10) UNSIGNED NOT NULL,
    relNum INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(30) NOT NULL,
    `body` LONGTEXT NOT NULL
);

DROP TABLE `like`
CREATE TABLE `like`(
    num INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberNum INT(10) UNSIGNED NOT NULL,
    relNum INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(30) NOT NULL,
    `point` TINYINT(1) UNSIGNED NOT NULL
);

DROP TABLE `board`
CREATE TABLE `board`(
    num INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(50) UNIQUE NOT NULL,
    `code` CHAR(50) UNIQUE NOT NULL
);

INSERT INTO board
    SET regDate = NOW(),
    updateDate = NOW(),
    `name` = '공지사항',
    `code` = 'notice'

INSERT INTO board
    SET regDate = NOW(),
    updateDate = NOW(),
    `name` = '자유게시판',
    `code` = 'free'

/* 기존 게시판 글 자유게시판으로 전부 이전 */
UPDATE article
SET boardNum = 2
WHERE boardNum =1

ALTER TABLE article DROP COLUMN replyNum

SELECT A.*,
M.name AS extra__writer,
COUNT(*) AS extra__reply
FROM article AS A
INNER JOIN `member` AS M
ON A.memberNum = M.num
LEFT JOIN `reply` AS RE
ON RE.relTypeCode = 'article'
WHERE A.num = RE.relNum
GROUP BY A.num

/* 1차 시도 */
SELECT A.*,
M.name AS extra__writer,
		IFNULL(SUM(L.point), 0) AS likePoint
		FROM article AS A
		INNER JOIN `member` AS M
		ON A.memberNum = M.num
		LEFT JOIN `like` AS L
		ON A.num = L.relNum
		WHERE 1
		AND A.num = 35
		GROUP BY A.num

/* 게시판에 맞는 게시물 갯수 찾기 */

SELECT COUNT(*)
    FROM article
    INNER JOIN board
    ON article.boardNum = board.num
    AND board.num = 2
    