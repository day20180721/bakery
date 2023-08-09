package com.littlejenny.bakery.generator.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.littlejenny.bakery.generator.dto.Order;
import com.littlejenny.bakery.generator.dto.OrderItem;
import com.littlejenny.bakery.generator.service.OrderService;
import com.littlejenny.bakery.generator.mapper.OrderMapper;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.utils.PageUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author user
 * @description 针对表【order】的数据库操作Service实现
 * @createDate 2023-07-11 16:57:35
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {
    @Cacheable(value = "orders", key = "getMethodName()+'-'+#page+'-'+#customerId")
    @Override
    public MyPage<Order> pageByCustomerId(Long page,String customerId) {
        return getBaseMapper().selectListByCustomerIdAndPage(customerId, PageUtil.of(page));
    }


    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "order", key = "#entity.id")
    })
    @Override
    public boolean updateById(Order entity) {
        return super.updateById(entity);
    }

    @Cacheable(value = "orders", key = "getMethodName()+'-'+#page.current")
    @Override
    public <E extends IPage<Order>> E page(E page) {
        return super.page(page);
    }

    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "order", key = "#entity.id")
    })
    @Override
    public boolean save(Order entity) {
        return super.save(entity);
    }
}




