package com.littlejenny.bakery.generator.mapper;

import java.util.Date;
import java.util.List;

import com.littlejenny.bakery.generator.dto.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author user
 * @description 针对表【cart】的数据库操作Mapper
 * @createDate 2023-07-13 23:08:55
 * @Entity com.littlejenny.bakery.generator.entity.Cart
 */
public interface CartMapper extends BaseMapper<Cart> {
    List<String> selectListReservationDayInSpecifiedYearAndMonthAndUserId(
            @Param("begin") Date begin, @Param("end") Date end, @Param("userId") String userId);
    Cart getOrNullByUserIdAndReservationDate(@Param("userId") String userId, @Param("reservationDate") Date reservationDate);
}




