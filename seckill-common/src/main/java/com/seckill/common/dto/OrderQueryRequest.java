package com.seckill.common.dto;

import lombok.Data;

@Data
public class OrderQueryRequest {
    private String orderNo; // 查单个订单时用
    private Long userId;    // 查用户订单列表时用
}
