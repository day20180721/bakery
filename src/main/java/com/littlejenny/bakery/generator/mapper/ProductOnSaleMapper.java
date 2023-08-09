package com.littlejenny.bakery.generator.mapper;
import java.util.List;

import com.littlejenny.bakery.generator.dto.OrderItem;
import org.apache.ibatis.annotations.Param;
import java.util.Date;

import com.littlejenny.bakery.generator.dto.ProductOnSale;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author user
* @description 针对表【product_on_sale】的数据库操作Mapper
* @createDate 2023-07-11 16:57:47
* @Entity com.littlejenny.bakery.generator.entity.ProductOnSale
*/
public interface ProductOnSaleMapper extends BaseMapper<ProductOnSale> {
    List<String> selectListOnSaleDayInSpecifiedYearAndMonth(@Param("begin")Date begin, @Param("end")Date end);
    Boolean addOnSaleProductReservation(@Param("item") OrderItem item, @Param("selledDate")Date sellDate);
    ProductOnSale getByProductIdAndSelledDate(@Param("productId") Integer productId, @Param("selledDate") Date selledDate);
    List<Integer> selectListIdBySelledDate(@Param("selledDate") Date selledDate);
    List<ProductOnSale> selectListBySelledDate(@Param("selledDate") Date selledDate);

}




