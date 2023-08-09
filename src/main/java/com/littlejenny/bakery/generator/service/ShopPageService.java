package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.vo.ShopProductVO;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ShopPageService {
    MyPage<ShopProductVO> pageShopProductByDateAndCategoryId(Date date, Integer categoryId, Long pageNumber);

    MyPage<ShopProductVO> pageShopProductByDate(Date date, Long pageNumber);

}
