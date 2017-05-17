CREATE TABLE `tb_picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pic_link` varchar(512) DEFAULT NULL COMMENT '图片url地址',
  `link_md5` varchar(32) DEFAULT NULL COMMENT '图片url地址MD5值',
  `local_pic_link` varchar(255) DEFAULT NULL COMMENT '本地图片地址',
  `image_status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '图片状态：1:本地化  0:未本地化  2 本地化失败',
  `createtime` int(11) unsigned DEFAULT NULL COMMENT '创建时间',
  `updatetime` int(11) unsigned DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_U_picture_MD5` (`link_md5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片表';


CREATE TABLE `tb_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source_id` int(11) NOT NULL COMMENT '来源id',
  `pic_id` int(11) NOT NULL COMMENT 'pic_id=tb_picture.id',
	`tag` VARCHAR(32) NOT NULL COMMENT '关键词',
  `createtime` int(11) unsigned DEFAULT NULL COMMENT '创建时间',
  `updatetime` int(11) unsigned DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
	KEY `IX_source_id` (`source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_photo_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source_name` VARCHAR(32) NOT NULL COMMENT '来源名',
  `createtime` int(11) unsigned DEFAULT NULL COMMENT '创建时间',
  `updatetime` int(11) unsigned DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;