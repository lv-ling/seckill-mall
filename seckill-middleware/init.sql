CREATE
DATABASE IF NOT EXISTS seckill_stock DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE
DATABASE IF NOT EXISTS seckill_order DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE
seckill_stock;

CREATE TABLE IF NOT EXISTS product
(
    id
    BIGINT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    name
    VARCHAR
(
    100
) NOT NULL,
    price DECIMAL
(
    10,
    2
) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS stock
(
    id
    BIGINT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    product_id
    BIGINT
    NOT
    NULL
    UNIQUE,
    quantity
    INT
    NOT
    NULL
    DEFAULT
    0,
    version
    INT
    NOT
    NULL
    DEFAULT
    0,
    update_time
    DATETIME
    DEFAULT
    CURRENT_TIMESTAMP
    ON
    UPDATE
    CURRENT_TIMESTAMP
);

USE
seckill_order;

CREATE TABLE IF NOT EXISTS orders
(
    id
    BIGINT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    order_no
    VARCHAR
(
    64
) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    status TINYINT NOT NULL DEFAULT 0 COMMENT '0-待支付 1-已创建 2-已取消',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
    );