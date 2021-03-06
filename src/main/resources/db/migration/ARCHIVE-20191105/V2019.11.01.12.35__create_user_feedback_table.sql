CREATE TABLE FOODIE_USER_FEEDBACK
(
    ID         VARCHAR(32) NOT NULL COMMENT 'ID' PRIMARY KEY,
    TITLE      VARCHAR(255) COMMENT '反馈标题',
    CONTENT    TEXT COMMENT '反馈内容',
    NAME       VARCHAR(100) COMMENT '反馈人姓名',
    EMAIL      VARCHAR(100) COMMENT '反馈人邮箱',
    USER_ID    VARCHAR(32) COMMENT '商家ID',
    CREATED_AT TIMESTAMP COMMENT '创建时间'
);

