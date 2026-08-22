package com.seckill.stock.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.common.entity.Product;
import com.seckill.common.entity.Stock;
import com.seckill.stock.mapper.ProductMapper;
import com.seckill.stock.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StockService extends ServiceImpl<StockMapper, Stock> {

    private final ProductMapper productMapper;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 初始化商品和库存
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public Long initStock(String name, BigDecimal price, Integer quantity) {
        // 1. 拆入商品
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        productMapper.insert(product);

        // 2. 插入库存
        Stock stock = new Stock();
        stock.setProductId(product.getId());
        stock.setQuantity(quantity);
        stock.setVersion(0);
        this.save(stock);

        // 3. 预热到 Redis
        String redisKey = "seckill:stock:" + product.getId();
        stringRedisTemplate.opsForValue().set(redisKey, String.valueOf(quantity));

        return product.getId();
    }

    /**
     * 扣减库存（数据库最终扣减）
     *
     * @return true=扣减成功 false=扣减失败
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deductStock(Long productId, Integer quantity) {
        // 使用乐观锁或条件更新，防止超卖
        // UPDATE stock SET quantity = quantity - ？ WHERE product_id = ? quantity >= ?
        int rows = baseMapper.deductStock(productId, quantity);
        return rows > 0;

    }
}
