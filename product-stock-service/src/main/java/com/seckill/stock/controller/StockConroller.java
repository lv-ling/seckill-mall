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
public class StockConroller {

    private final StockService stockService;

    /**
     * 初始化商品库存
     * 实例：POST /stock/init?name=iPhone16&price=5999&quantity=100
     * */
    @PostMapping("/init")
    public Map<String, Object> initStock(@RequestParam("name") String name,
                                         @RequestParam("price") BigDecimal price,
                                         @RequestParam("quantity") Integer quantity){
        Long productId = stockService.initStock(name,price,quantity);

        Map<String, Object> result = new HashMap<>();
        result.put("code",200);
        result.put("msg","初始化成功");
        result.put("productId",productId);
        return result;
    }
}
