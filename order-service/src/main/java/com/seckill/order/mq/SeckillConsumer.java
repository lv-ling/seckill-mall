package com.seckill.order.mq;

import com.seckill.common.dto.SeckillMessage;
import com.seckill.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "seckill-success-topic", consumerGroup = "order-consumer-group")
public class SeckillConsumer implements RocketMQListener<SeckillMessage> {
    private final OrderService orderService;

    @Override
    public void onMessage(SeckillMessage message) {
        log.info("收到秒杀成功消息：{}", message);

        try {
            orderService.createOrder(message.getUserId(), message.getProductId(), message.getQuantity());
            log.info("订单创建成功，userId={},productId={}", message.getUserId(), message.getProductId());
        } catch (Exception e) {
            log.error("创建订单失败", e);
        }

    }
}
