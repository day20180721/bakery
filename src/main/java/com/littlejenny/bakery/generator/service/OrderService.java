package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.generator.dto.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.littlejenny.bakery.generator.dto.OrderItem;
import com.littlejenny.bakery.pojo.MyPage;

import java.util.List;

/**
* @author user
* @description 针对表【order】的数据库操作Service
* @createDate 2023-07-11 16:57:35
*/
public interface OrderService extends IService<Order> {

    MyPage<Order> pageByCustomerId(Long page,String uuid);

}
