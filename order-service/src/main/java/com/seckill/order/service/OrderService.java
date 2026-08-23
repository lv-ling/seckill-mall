package com.seckill.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.common.entity.Order;
import com.seckill.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Long userId, Long productId, Integer quantity) {
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setStatus(1);
        order.setCreateTime(LocalDateTime.now());

        this.save(order);
    }

    private String generateOrderNo() {
        return "SO" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
