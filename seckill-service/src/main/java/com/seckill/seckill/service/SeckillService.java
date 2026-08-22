package com.seckill.seckill.service;

import com.seckill.common.dto.SeckillMessage;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeckillService {

    private final StringRedisTemplate stringRedisTemplate;
    private final RocketMQTemplate rocketMQTemplate;

    private static final String STOCK_KEY_PREFIX = "seckill:stock:";

    /**
     * 秒杀核心逻辑
     *
     */
    public boolean doSeckill(Long userId, Long productId, Integer quantity) {
        String stockKey = STOCK_KEY_PREFIX + productId;

        System.out.println("reddisKey:" + stockKey);

        System.out.println("quantity" + quantity);

        // 1. Redis 预减库存
        Long remain = stringRedisTemplate.opsForValue().decrement(stockKey, quantity);

        System.out.println("remain" + remain);

        if (remain == null || remain < 0) {
            // 库存不足，回滚添加回去
            if (remain != null && remain < 0) {
                stringRedisTemplate.opsForValue().increment(stockKey, quantity);
            }
            return false;
        }

        // 2. 预减成功，发送 MQ消息
        SeckillMessage message = new SeckillMessage();
        message.setUserId(userId);
        message.setProductId(productId);
        message.setQuantity(quantity);

        rocketMQTemplate.convertAndSend("seckill-success-topic", message);

        return true;
    }
}
