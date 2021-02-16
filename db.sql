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