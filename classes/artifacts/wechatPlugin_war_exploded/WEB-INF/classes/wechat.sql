CREATE TABLE 'userid_openid'(
  'userid' VARCHAR (32) NOT  NULL COMMENT '平台用户id',
  'openid' VARCHAR (32) NOT  NULL COMMENT '微信用户关联公众号id',
  PRIMARY  KEY ('userid')
)COMMENT 'id映射表';