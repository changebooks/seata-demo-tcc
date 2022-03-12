DROP TABLE IF EXISTS undo_log;
CREATE TABLE undo_log
(
    branch_id     bigint(20)   NOT NULL COMMENT 'branch transaction id',
    xid           varchar(128) NOT NULL COMMENT 'global transaction id',
    context       varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    rollback_info longblob     NOT NULL COMMENT 'rollback info',
    log_status    int(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    log_created   datetime(6)  NOT NULL COMMENT 'create datetime',
    log_modified  datetime(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE INDEX ux_undo_log (xid, branch_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = 'AT transaction mode undo table';

DROP TABLE IF EXISTS account;
CREATE TABLE account
(
    user_id        int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
    usable_balance int(11) unsigned NOT NULL DEFAULT '0' COMMENT '可用余额，单位：分',
    frozen_balance int(11) unsigned NOT NULL DEFAULT '0' COMMENT '冻结余额，单位：分',
    PRIMARY KEY (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '预存款';

INSERT INTO account
VALUES (1, 100, 0);

DROP TABLE IF EXISTS account_flow;
CREATE TABLE account_flow
(
    id             int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水id',
    user_id        int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
    change_type    int(11) unsigned NOT NULL DEFAULT '0' COMMENT '改变方式，0-未知、1-冻结、2-解冻、3-扣已冻',
    usable_balance int(11) unsigned NOT NULL DEFAULT '0' COMMENT '增减的可用余额，单位：分',
    frozen_balance int(11) unsigned NOT NULL DEFAULT '0' COMMENT '增减的冻结余额，单位：分',
    order_id       int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单号',
    idempotent     varchar(255)     NOT NULL DEFAULT '' COMMENT '幂等，事务id',
    created_at     datetime         NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE INDEX uk_idempotent (user_id, change_type, idempotent)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '预存款流水';

DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory
(
    product_id int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
    stock_num  int(11) unsigned NOT NULL DEFAULT '0' COMMENT '当前库存',
    PRIMARY KEY (product_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '库存';

INSERT INTO inventory
VALUES (1, 100);

DROP TABLE IF EXISTS inventory_flow;
CREATE TABLE inventory_flow
(
    id          int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水id',
    product_id  int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
    change_type int(11) unsigned NOT NULL DEFAULT '0' COMMENT '改变方式，0-未知、1-入库、2-出库',
    stock_num   int(11) unsigned NOT NULL DEFAULT '0' COMMENT '出入的库存',
    order_id    int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单号',
    idempotent  varchar(255)     NOT NULL DEFAULT '' COMMENT '幂等，事务id',
    created_at  datetime         NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE INDEX uk_idempotent (product_id, change_type, idempotent)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '库存流水';

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id          int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单号',
    user_id     int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
    product_id  int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
    product_num int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品数',
    pay_num     int(11) unsigned NOT NULL DEFAULT '0' COMMENT '支付金额，单位：分',
    pay_status  int(11) unsigned NOT NULL DEFAULT '0' COMMENT '支付状态，0-未知、1-未支付、2-支付成功、3-支付失败',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '订单';
