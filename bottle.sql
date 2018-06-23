
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

