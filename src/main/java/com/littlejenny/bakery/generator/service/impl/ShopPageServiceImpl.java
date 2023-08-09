package com.littlejenny.bakery.generator.service.impl;

import com.littlejenny.bakery.generator.mapper.ShopPageMapper;
import com.littlejenny.bakery.generator.service.ShopPageService;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.utils.DateUtil;
import com.littlejenny.bakery.utils.PageUtil;
import com.littlejenny.bakery.vo.ShopProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShopPageServiceImpl implements ShopPageService {
    @Autowired
    ShopPageMapper shopMapper;
    @Override
    public MyPage<ShopProductVO> pageShopProductByDateAndCategoryId(Date date, Integer categoryId, Long page) {
        return shopMapper.selectPageShopProductVOByDateAndCategoryId(date,categoryId,PageUtil.of(page));
    }

    @Override
    public MyPage<ShopProductVO> pageShopProductByDate(Date date, Long page) {
        return shopMapper.selectPageShopProductVOByDate(date,PageUtil.of(page));

    }
}
