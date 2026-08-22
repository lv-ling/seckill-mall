package com.seckill.seckill.controller;

import com.seckill.seckill.service.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/seckill")
@RequiredArgsConstructor
public class SeckillController {

    private final SeckillService seckillService;

    /**
     * 秒杀接口
     * 示例：POST /api/seckill?userId=1001&productId=1&quantity=1
     *
     */
    @PostMapping
    public Map<String, Object> seckill(@RequestParam("userId") Long userId,
                                       @RequestParam("productId") Long productId,
                                       @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {

        boolean success = seckillService.doSeckill(userId, productId, quantity);

        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("msg", "秒杀成功，订单生成中");
        } else {
            result.put("code", 500);
            result.put("msg", "库存不足，秒杀失败");
        }
        return result;
    }
}
