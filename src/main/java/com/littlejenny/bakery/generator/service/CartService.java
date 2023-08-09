package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.generator.dto.Cart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
* @author user
* @description 针对表【cart】的数据库操作Service
* @createDate 2023-07-13 23:08:55
*/
public interface CartService extends IService<Cart> {
    List<String> listReservationDayListByYearAndMonthAndUserId(Integer year, Integer month, String userId);
    List<String> listReservationDayListByYearAndMonthAndUserId(String yearMonth, String userId);
    boolean removeCartAndItemByUserIdAndReservationDate(String userId, Date date);
    Cart getOrNullByUserIdAndDate(String userId, Date date)  ;
    Cart newAndSave(String userId, Date reservationDate) ;
}
