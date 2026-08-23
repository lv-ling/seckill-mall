package com.seckill.common.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Order {
    private Long id;
    private Long userId;
    private String orderNo;
    private  Long productId;
    private Integer quantity;
    private  Integer status; // 0-待支付 1-已创建 2-已取消
    private  LocalDateTime createTime;
}
