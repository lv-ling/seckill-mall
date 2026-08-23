package com.seckill.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.common.entity.Order;
import com.seckill.order.feign.StockFeignClient;
import com.seckill.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    private final StockFeignClient stockFeignClient;

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

        // 2. 调用库存服务，最终扣减 MySQL 库存
        Map<String, Object> result = stockFeignClient.deductStock(productId, quantity);
        log.info("调用库存服务扣减结果: {}", result);

        // 简单判断（根据你库存服务返回的 code）
        if (result == null || !Integer.valueOf(200).equals(result.get("code"))) {
            // 扣减失败，可以抛异常让事务回滚，或者记录日志做补偿
            throw new RuntimeException("库存扣减失败: " + result);
        }
    }

    private String generateOrderNo() {
        return "SO" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
