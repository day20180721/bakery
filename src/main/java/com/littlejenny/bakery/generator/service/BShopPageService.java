package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.generator.dto.Product;
import com.littlejenny.bakery.to.BShopProductTO;
import com.littlejenny.bakery.vo.BShopProductVO;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface BShopPageService {
    List<BShopProductVO> listOnSaleProductByDate(Date date) throws ParseException;
    List<Product> listNotOnSaleProductByDate(Date date) throws ParseException;
    Boolean saveOrUpdateBatch(List<BShopProductTO> products, Date date) ;

}
