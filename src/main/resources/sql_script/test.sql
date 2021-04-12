-- 创建用户表，用户名为唯一键
CREATE TABLE `user`
(
    `id`       INT(11)     NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(32) NOT NULL COMMENT 'the username',
    `password` VARCHAR(64) NOT NULL COMMENT 'the password',
    `created`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unq_username` (`username`)
) ENGINE = InnoDB
    -- 自增从 100000 开始
  AUTO_INCREMENT = 1000000
    -- 默认字符集，mysql 的 utf8 是 3 字节编码，无法兼容某些生僻字或者表情符号，所以一定要用 utf8mb4，这个才对应标准的 utf-8 编码
  DEFAULT CHARSET = utf8mb4 COMMENT ='user table';

-- 创建几个测试用户
INSERT INTO test.user (id, username, password, created)
VALUES (1000003, 'test_username_4', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (1000004, 'test_username_5', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (1000005, 'test_username_6', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (1000006, 'test_username_7', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (1000007, 'test_username_8', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (1000008, 'test_username_9', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (1000009, 'test_username_10', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000010, 'test_username_11', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000011, 'test_username_12', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000012, 'test_username_13', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000013, 'test_username_14', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000014, 'test_username_15', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000015, 'test_username_16', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000016, 'test_username_17', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000017, 'test_username_18', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000018, 'test_username_19', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000019, 'test_username_20', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000020, 'test_username_21', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000021, 'test_username_22', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000022, 'test_username_23', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000023, 'test_username_24', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000024, 'test_username_25', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000025, 'test_username_26', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000026, 'test_username_27', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000027, 'test_username_28', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000028, 'test_username_29', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000029, 'test_username_30', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000030, 'test_username_31', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000031, 'test_username_32', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000032, 'test_username_33', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000033, 'test_username_34', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000034, 'test_username_35', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000035, 'test_username_36', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000036, 'test_username_37', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000037, 'test_username_38', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000038, 'test_username_39', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000039, 'test_username_40', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000040, 'test_username_41', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000041, 'test_username_42', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000042, 'test_username_43', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000043, 'test_username_44', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000044, 'test_username_45', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000045, 'test_username_46', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000046, 'test_username_47', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000047, 'test_username_48', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000048, 'test_username_49', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000049, 'test_username_50', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000050, 'test_username_51', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000051, 'test_username_52', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000052, 'test_username_53', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000053, 'test_username_54', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000054, 'test_username_55', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000055, 'test_username_56', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000056, 'test_username_57', 'test_password', '2019-07-02 06:15:22');
INSERT INTO test.user (id, username, password, created)
VALUES (10000057, 'test_username_58', 'test_password', '2019-07-02 06:15:22');