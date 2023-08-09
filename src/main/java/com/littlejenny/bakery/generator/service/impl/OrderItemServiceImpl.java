package com.littlejenny.bakery.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.littlejenny.bakery.generator.dto.Order;
import com.littlejenny.bakery.generator.dto.OrderItem;
import com.littlejenny.bakery.generator.service.OrderItemService;
import com.littlejenny.bakery.generator.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author user
* @description 针对表【order_item】的数据库操作Service实现
* @createDate 2023-07-11 16:57:39
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService{

    @Override
    public List<OrderItem> getByOrderId(Long orderId) {
        return getBaseMapper().getByOrderId(orderId);
    }
}




