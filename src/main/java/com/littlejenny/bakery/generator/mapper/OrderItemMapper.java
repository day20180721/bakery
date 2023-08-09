package com.littlejenny.bakery.generator.mapper;

import com.littlejenny.bakery.generator.dto.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author user
* @description 针对表【order_item】的数据库操作Mapper
* @createDate 2023-07-11 16:57:39
* @Entity com.littlejenny.bakery.generator.entity.OrderItem
*/
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    List<OrderItem> getByOrderId(@Param("orderId") Long orderId);
}




