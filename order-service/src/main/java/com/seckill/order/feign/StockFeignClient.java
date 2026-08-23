package com.seckill.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "product-stock-service", url = "http://127.0.0.1:8081")
public interface StockFeignClient {

    @PostMapping("/stock/deduct")
    Map<String, Object> deductStock(@RequestParam("productId") Long productId,
                                    @RequestParam("quantity") Integer quantity);
}