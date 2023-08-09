package com.littlejenny.bakery.generator.mapper;

import com.littlejenny.bakery.generator.dto.CartItem;
import com.littlejenny.bakery.vo.CartItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface CartPageMapper {
    CartItemVO getCartItemVOByProductIdAndCount(@Param("productId") Integer productId, @Param("count") Integer count, @Param("date") Date selledDate);
    CartItemVO getCartItemVOByCartItemAndReservationDate(@Param("cartItem")CartItem cartItem,@Param("reservationDate")Date reservationDate);
}
