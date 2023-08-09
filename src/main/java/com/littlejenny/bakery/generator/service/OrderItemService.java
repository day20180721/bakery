package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.generator.dto.Order;
import com.littlejenny.bakery.generator.dto.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author user
* @description 针对表【order_item】的数据库操作Service
* @createDate 2023-07-11 16:57:39
*/
public interface OrderItemService extends IService<OrderItem> {

    List<OrderItem> getByOrderId(Long orderId);
}
