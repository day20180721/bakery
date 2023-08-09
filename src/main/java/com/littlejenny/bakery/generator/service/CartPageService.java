package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.vo.CartItemVO;
import com.littlejenny.bakery.to.CartItemTO;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface CartPageService {
    List<CartItemVO> listCartItemVOByUserIdAndDate(String userId, Date date) throws ParseException;

}
