
CREATE DATABASE willSea;

USE willSea;

CREATE TABLE w_user (
  uid int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  utype VARCHAR(16) DEFAULT 'user',
  uname VARCHAR(64) DEFAULT NULL,
  upassword VARCHAR(64) DEFAULT NULL,
  uemail VARCHAR(64) DEFAULT NULL,
  PRIMARY KEY (uid),
  UNIQUE KEY (uname),
  UNIQUE KEY (uemail)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE w_bottle (
  bid int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  aid int(10) UNSIGNED DEFAULT '0',
  btime int(10) UNSIGNED DEFAULT '0',
  btype VARCHAR(16) DEFAULT 'text',
  blookTime int(10) UNSIGNED DEFAULT '0',
  comment_sum int(10) UNSIGNED DEFAULT '0',
  title VARCHAR(36) DEFAULT 'undefined',

  PRIMARY KEY (bid),
  FOREIGN KEY (aid) REFERENCES w_user(uid)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE w_comment (
  cid int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  aid int(10) UNSIGNED DEFAULT '0',
  bid int(10) UNSIGNED DEFAULT '0',
  ctime int(10) UNSIGNED DEFAULT '0',
  content TEXT COMMENT '文字内容',
  ctype VARCHAR(16) DEFAULT 'text',

  PRIMARY KEY (cid),
  FOREIGN KEY (aid) REFERENCES w_user(uid),
  FOREIGN KEY (bid) REFERENCES w_bottle(bid)


)ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table w_bottle add content VARCHAR(36) DEFAULT '0';

alter table w_bottle change btype bottleType varchar(24) DEFAULT '0';

# 黑名单表
CREATE TABLE w_blacklist(
  black_id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  source_uid int(10) UNSIGNED DEFAULT '0',
  target_uid int(10) UNSIGNED DEFAULT '0',

  PRIMARY KEY (black_id),
  FOREIGN KEY (source_uid) REFERENCES w_user(uid),
  FOREIGN KEY (target_uid) REFERENCES w_user(uid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 特别关注表
CREATE TABLE w_favorite(
  favorite_id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  source_uid int(10) UNSIGNED DEFAULT '0',
  target_uid int(10) UNSIGNED DEFAULT '0',

  PRIMARY KEY (favorite_id),
  FOREIGN KEY (source_uid) REFERENCES w_user(uid),
  FOREIGN KEY (target_uid) REFERENCES w_user(uid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



insert into w_bottle(aid, btime, btype, title, content, private)
  values(12, 20180601, 'text', 'helpful', 'it is content', 'true');

insert into w_bottle(aid, btime, btype, title, content, private)
values(12, 20180601, 'text', 'success', 'it is content', 'true');


insert into w_bottle(aid, btime, btype, title, content, private)
values(14, 20180601, 'text', '12312', 'it is content', 'true');

insert into w_bottle(aid, btime, btype, title, content, private)
values(14, 20180601, 'text', 'asdas', 'it is content', 'true');

insert into w_bottle(aid, btime, btype, title, content, private)
values(14, 20180601, 'text', 'asdsa', 'it is content', 'true');

insert into w_bottle(aid, btime, btype, title, content, private)
values(13, 20180601, 'text', 'zxczxczx', 'it is content', 'true');

insert into w_bottle(aid, btime, btype, title, content, private)
values(13, 20180601, 'text', 'xzcczxczxc', 'it is content', 'false');

insert into w_bottle(aid, btime, btype, title, content, private)
values(13, 20180601, 'text', 'helpfzxczxul', 'it is content', 'true');

insert into w_bottle(aid, btime, btype, title, content, private)
values(15, 20180601, 'text', 'xzczx', 'it is content', 'true');

insert into w_bottle(aid, btime, btype, title, content, private)
values(16, 20180601, 'text', 'xzczxczxcx', 'it is content', 'false');



#  user   性别， 电话，  地区， 出生日期， 头像（URL）

alter TABLE w_user ADD COLUMN sex INT(2);
alter TABLE w_user ADD COLUMN telephone INT(32);
alter TABLE w_user ADD COLUMN location VARCHAR(24);
alter TABLE w_user ADD COLUMN birthday INT(32);
alter TABLE w_user ADD COLUMN head VARCHAR(32);

alter TABLE w_user CHANGE head headImageUrl VARCHAR(32);


insert into w_comment(aid, bid, ctime, content) values (16, 25, 20180608, 'where are you now?');
insert into w_comment(aid, bid, ctime, content) values (16, 25, 20180607, 'Objects can be obtained by means of either dependency lookup or dependency injection.');
insert into w_comment(aid, bid, ctime, content) values (16, 25, 20180607, ' instead a Spring configuration file defines which services and components must be called. ');
insert into w_comment(aid, bid, ctime, content) values (16, 25, 20180607, 'This IoC is intended to increase the ease of maintenance and testing.');
insert into w_comment(aid, bid, ctime, content) values (16, 25, 20180607, 'Spring AOP has been designed to make it able to work with cross-cutting concerns inside the Spring Framework.');
insert into w_comment(aid, bid, ctime, content) values (16, 25, 20180607, 'Any object which is created and configured by the container can be enriched using Spring AOP.');
insert into w_comment(aid, bid, ctime, content) values (16, 25, 20180607, 'The Spring Framework uses Spring AOP internally for transaction management, security, remote access, and JMX.');
insert into w_comment(aid, bid, ctime, content) values (16, 25, 20180607, 'Since version 2.0 of the framework, Spring provides two approaches to the AOP configuration:');
insert into w_comment(aid, bid, ctime, content) values (15, 24, 20180607, 'The Spring Framework uses Spring AOP internally for transaction management, security, remote access, and JMX.');
insert into w_comment(aid, bid, ctime, content) values (15, 24, 20180607, 'Since version 2.0 of the framework, Spring provides two approaches to the AOP configuration:');

update w_bottle set btext = "qewqweqasdas";

