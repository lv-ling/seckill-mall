package com.seckill.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeckillMessage implements Serializable {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
