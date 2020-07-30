CREATE TABLE `ACT_FO_FORM_MODEL`
(
    `ID_`               varchar(64) COLLATE utf8_bin NOT NULL,
    `REV_`              int(11)                           DEFAULT NULL,
    `NAME_`             varchar(255) COLLATE utf8_bin     DEFAULT NULL,
    `KEY_`              varchar(255) COLLATE utf8_bin     DEFAULT NULL,
    `CATEGORY_`         varchar(255) COLLATE utf8_bin     DEFAULT NULL,
    `CREATE_TIME_`      timestamp(3)                 NULL DEFAULT CURRENT_TIMESTAMP(3),
    `LAST_UPDATE_TIME_` timestamp(3)                 NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    `FORM_JSON_`        varchar(5000) COLLATE utf8_bin    DEFAULT NULL,
    `VERSION_`          int(11)                           DEFAULT NULL,
    `DESCRIPTION_`      varchar(255) COLLATE utf8_bin     DEFAULT NULL,
    `DEPLOYMENT_ID_`    varchar(64) COLLATE utf8_bin      DEFAULT NULL,
    `TENANT_ID_`        varchar(255) COLLATE utf8_bin     DEFAULT '',
    PRIMARY KEY (`ID_`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;