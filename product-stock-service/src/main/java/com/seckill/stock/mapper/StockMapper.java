package com.seckill.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seckill.common.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {

    @Update("UPDATE stock SET quantity = quantity - #{quantity} WHERE product_id = #{productId} AND quantity >= #{quantity}")
    int deductStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);

}
