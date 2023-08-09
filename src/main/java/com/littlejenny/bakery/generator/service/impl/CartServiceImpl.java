package com.littlejenny.bakery.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.littlejenny.bakery.generator.dto.Cart;
import com.littlejenny.bakery.generator.service.CartItemService;
import com.littlejenny.bakery.generator.service.CartService;
import com.littlejenny.bakery.generator.mapper.CartMapper;
import com.littlejenny.bakery.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author user
 * @description 针对表【cart】的数据库操作Service实现
 * @createDate 2023-07-13 23:08:55
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart>
        implements CartService {
    @Override
    public List<String> listReservationDayListByYearAndMonthAndUserId(Integer year, Integer month, String userId) {
        Date begin = DateUtil.getFirstDayByGivenYearAndMonth(year, month);
        Date end = DateUtil.getLastDayByGivenYearAndMonth(year, month);
        List<String> dates = getBaseMapper().selectListReservationDayInSpecifiedYearAndMonthAndUserId(begin, end, userId);
        return DateUtil.datesStringToGetDays(dates);
    }

    @Override
    public List<String> listReservationDayListByYearAndMonthAndUserId(String yearMonth, String userId) {
        Integer[] yearMonthDateArray = DateUtil.dateStringSplitToYearMonthDayIntArray(yearMonth);
        Integer year = yearMonthDateArray[0];
        Integer month = yearMonthDateArray[1];
        return listReservationDayListByYearAndMonthAndUserId(year, month, userId);
    }

    @Autowired
    CartItemService cartItemService;

    @Transactional
    @Override
    @CacheEvict(value = "cart", key = "#userId+'-'+#date")
    public boolean removeCartAndItemByUserIdAndReservationDate(String userId, Date date)  {
        Cart cart = getOrNullByUserIdAndDate(userId, date);
        if (cart != null) {
            return cartItemService.removeByCartId(cart.getId()) && removeById(cart);
        }
        return false;
    }

    @Cacheable(value = "cart", key = "#userId+'-'+#date")
    public Cart getOrNullByUserIdAndDate(String userId, Date date)  {
        return getBaseMapper().getOrNullByUserIdAndReservationDate(userId, date);
    }

    @CachePut(value = "cart", key = "#userId+'-'+#reservationDate")
    public Cart newAndSave(String userId, Date reservationDate)  {
        Cart cart = new Cart(null, reservationDate, userId);
        save(cart);
        return cart;
    }
}




