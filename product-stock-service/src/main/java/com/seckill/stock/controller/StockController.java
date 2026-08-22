package com.seckill.stock.controller;

import com.seckill.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    /**
     * 初始化商品库存
     * 实例：POST /stock/init?name=iPhone16&price=5999&quantity=100
     *
     */
    @PostMapping("/init")
    public Map<String, Object> initStock(@RequestParam("name") String name,
                                         @RequestParam("price") BigDecimal price,
                                         @RequestParam("quantity") Integer quantity) {
        Long productId = stockService.initStock(name, price, quantity);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "初始化成功");
        result.put("productId", productId);
        return result;
    }

    /**
     * 扣减库存
     * 示例：POST /stock/deduct?productId=1&quantity=1
     */
    @PostMapping("/deduct")
    public Map<String, Object> deductStock(@RequestParam("productId") Long productId,
                                           @RequestParam("quantity") Integer quantity) {
        boolean success = stockService.deductStock(productId, quantity);

        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("msg", "扣减成功");
        } else {
            result.put("code", 500);
            result.put("msg", "库存不足");
        }

        return result;

    }
}
