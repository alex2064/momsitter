CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '회원번호(key)',
  `name` varchar(10) NOT NULL COMMENT '이름',
  `birth_date` date NOT NULL COMMENT '생년월일',
  `gender` varchar(5) NOT NULL COMMENT '성별',
  `login_id` varchar(30) NOT NULL COMMENT '아이디',
  `password` varchar(100) NOT NULL COMMENT '비밀번호',
  `email` varchar(30) NOT NULL COMMENT '이메일',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_login_id` (`login_id`)
);

CREATE TABLE `sitter` (
  `id` bigint(20) NOT NULL COMMENT 'key',
  `user_id` bigint(20) DEFAULT NULL COMMENT '회원번호(user.key)',
  `fr_care_age` int(11) NOT NULL COMMENT '케어 가능 연령 from',
  `to_care_age` int(11) NOT NULL COMMENT '케어 가능 연령 to',
  `introduce` text NOT NULL COMMENT '자기 소개',
  PRIMARY KEY (`id`),
  KEY `fk_sitter_user_id` (`user_id`),
  CONSTRAINT `fk_sitter_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `parents` (
  `id` bigint(20) NOT NULL COMMENT 'key',
  `user_id` bigint(20) DEFAULT NULL COMMENT '회원번호(user.key)',
  `apply_info` text NOT NULL COMMENT '신청 내용',
  PRIMARY KEY (`id`),
  KEY `fk_parents_user_id` (`user_id`),
  CONSTRAINT `fk_parents_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `children` (
  `id` bigint(20) NOT NULL COMMENT 'key',
  `parents_id` bigint(20) DEFAULT NULL COMMENT 'parents.key',
  `birth_date` date NOT NULL COMMENT '생년월일',
  `gender` varchar(5) NOT NULL COMMENT '성별',
  PRIMARY KEY (`id`),
  KEY `fk_children_parents_id` (`parents_id`),
  CONSTRAINT `fk_children_parents_id` FOREIGN KEY (`parents_id`) REFERENCES `parents` (`id`)
);