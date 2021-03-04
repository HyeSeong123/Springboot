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

# APIkey
ALTER TABLE `member` CHANGE COLUMN authKey CHAR(80)

UPDATE `member`
SET authKey = CONCAT("authKey1__" , UUID(), "__", RAND())
WHERE authKey=''

UPDATE `member`
SET hpNum = '010-2010-2020'
WHERE hpNum = ''
# 멤버 번호 재지정
ALTER TABLE `member` AUTO_INCREMENT = 1;
# 멤버 패스워드 암호화
UPDATE `member`
SET loginPw = SHA2(loginPw, 256);

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

DROP TABLE attr
CREATE TABLE attr(
    num INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `relTypeCode` CHAR(20) NOT NULL,
    `relNum` INT(10) UNSIGNED NOT NULL,
    `typeCode` CHAR(30) NOT NULL,
    `type2Code` CHAR(30) NOT NULL,
    `value` TEXT NOT NULL
);

# attr 유니크 인덱스 걸기
ALTER TABLE `attr` DROP INDEX relTypeCode;
ALTER TABLE `attr` ADD UNIQUE INDEX (`relTypeCode`, `relNum`, `typeCode`, `type2Code`);  
## 변수찾는 속도 최적화
ALTER TABLE `attr` ADD INDEX(`relTypeCode`,`typeCode`, `type2Code`);

# attr에 만료날짜 추가
ALTER TABLE `attr` ADD COLUMN `expireDate` DATETIME NULL AFTER `value`;


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
    
SELECT *
    FROM `member`
    WHERE 1
    AND `name` = '방혜성'
    AND email = 'banggu1997@naver.com'

UPDATE `member`
SET authKey = 'authKey1__066f8df0-7cb3-11eb-bc64-b025aa3ecfdd__0.532693481568893' 
WHERE num=1

UPDATE `member`
SET authKey = 'authKey1__066f900e-7cb3-11eb-bc64-b025aa3ecfdd__0.809797994615322' 
WHERE num=2

UPDATE `member`
SET authKey = 'authKey1__ecacb00e-7cb7-11eb-bc64-b025aa3ecfdd__0.6244505845237994' 
WHERE num=3

SELECT DATE_FORMAT(A.regDate, '%Y-%m-%d %H:%i') AS regDate FROM article AS A

SELECT A.num,
        DATE_FORMAT(A.regDate, '%Y-%m-%d %H시%i분') AS regDate,
        DATE_FORMAT(A.updateDate, '%Y-%m-%d %H시%i분') AS updateDate,
        A.memberNum,
        A.boardNum,
        A.title,
        A.body,
		M.name AS extra__writer,
		B.name AS extra__board,
		IFNULL(SUM(L.point), 0) AS likePoint
		FROM article AS A
		INNER JOIN MEMBER AS M
		ON A.memberNum = M.num
		INNER JOIN board AS B
		ON A.boardNum = B.num
		LEFT JOIN `like` AS L
		ON A.num = L.relNum
		WHERE 1
		AND B.code = 'free'
		AND REPLACE(A.regDate, '-', '') LIKE CONCAT('%', REPLACE('202103', '-', '') , '%')

GROUP BY A.num DESC

INSERT INTO `member`
		 SET regDate = NOW(),
			updateDate = NOW(),
			loginId = 'test6',
			loginPw = '0ffe1abd1a08215353c233d6e009613e95eec4253832a761af28ff37ac5a150c',
			`name` = '방혜성',
			nickname = 'baobab0612',
			hpNum = '010-8370-0420',
			email = 'baobab0612@naver.com',
			authKey = CONCAT("authKey1__" , UUID(), "__", RADN())