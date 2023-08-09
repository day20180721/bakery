package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.generator.dto.Order;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.to.OrderTO;
import com.littlejenny.bakery.vo.OrderVO;

import java.text.ParseException;
import java.util.Date;

public interface OrderPageService {
    void saveByUserIdAndDateAndOrderTO(String userId, Date date, OrderTO orderTO) throws ParseException;

    MyPage<OrderVO> pageByCustomerId(Long page, String customerId);
}
