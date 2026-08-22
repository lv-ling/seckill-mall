package com.seckill.common.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Stock {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer version;
    private LocalDateTime updateTime;
}
