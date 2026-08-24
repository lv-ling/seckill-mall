package com.seckill.order.controller;


import com.seckill.common.dto.OrderQueryRequest;
import com.seckill.common.entity.Order;
import com.seckill.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/queryOrderByOrderNo")
    public Map<String, Object> getOrder(@RequestBody OrderQueryRequest orderQueryRequest) {
        Order order = orderService.getByOrderNo(orderQueryRequest.getOrderNo());

        Map<String, Object> result = new HashMap<>();

        if (order != null) {
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", order);
        } else {
            result.put("code", 404);
            result.put("msg", "订单不存在");
        }

        return result;

    }

    @PostMapping("/queryListByUserId")
    public Map<String, Object> getOrderList(@RequestBody OrderQueryRequest orderQueryRequest) {
        List<Order> orderList = orderService.listByUserId(orderQueryRequest.getUserId());

        Map<String, Object> result = new HashMap<>();

        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("data", orderList);

        return result;

    }

}
