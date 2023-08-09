package com.littlejenny.bakery.generator.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.littlejenny.bakery.generator.dto.CartItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author user
* @description 针对表【cart_item】的数据库操作Mapper
* @createDate 2023-07-13 23:06:38
* @Entity com.littlejenny.bakery.generator.entity.CartItem
*/
public interface CartItemMapper extends BaseMapper<CartItem> {
    List<CartItem> selectListByCartId(@Param("cartId") Long cartId);
    CartItem getByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Integer productId);
    boolean deleteByCartId(@Param("cartId") Long cartId);
}




