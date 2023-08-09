package com.littlejenny.bakery.generator.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.littlejenny.bakery.pojo.MyPage;
import org.apache.ibatis.annotations.Param;

import com.littlejenny.bakery.generator.dto.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author user
* @description 针对表【order】的数据库操作Mapper
* @createDate 2023-07-11 16:57:35
* @Entity com.littlejenny.bakery.generator.entity.Order
*/
public interface OrderMapper extends BaseMapper<Order> {
    List<Order> selectListByCustomerId(@Param("customerId") String customerId);

    MyPage<Order> selectListByCustomerIdAndPage(@Param("customerId")String customerId, IPage<Order> page);
}




