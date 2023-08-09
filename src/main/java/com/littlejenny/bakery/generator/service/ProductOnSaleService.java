package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.generator.dto.OrderItem;
import com.littlejenny.bakery.generator.dto.ProductOnSale;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
* @author user
* @description 针对表【product_on_sale】的数据库操作Service
* @createDate 2023-07-11 16:57:47
*/
public interface ProductOnSaleService extends IService<ProductOnSale> {
    List<String> listOnSaleDayListInSpecifiedYearAndMonth(Integer year, Integer month);
    List<String> listOnSaleDayListInSpecifiedYearAndMonth(String yearMonth);
    List<Integer> listIdByDate(Date date) ;
    List<ProductOnSale> listByDate(Date date) ;
    Boolean addReservation(OrderItem orderItem,Date date);
    BigDecimal getPriceOrNewPrice(Date date,Integer productId) ;
    boolean removeBatchByIdsAndDate(List<Integer> idList, Date date);

    ProductOnSale getByProductIdAndSelledDate(Integer productId, Date sellDate);
}
