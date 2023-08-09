package com.littlejenny.bakery.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.littlejenny.bakery.generator.dto.OrderItem;
import com.littlejenny.bakery.generator.dto.Product;
import com.littlejenny.bakery.generator.dto.ProductOnSale;
import com.littlejenny.bakery.generator.service.ProductOnSaleService;
import com.littlejenny.bakery.generator.mapper.ProductOnSaleMapper;
import com.littlejenny.bakery.generator.service.ProductService;
import com.littlejenny.bakery.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author user
 * @description 针对表【product_on_sale】的数据库操作Service实现
 * @createDate 2023-07-11 16:57:47
 */
@Service
public class ProductOnSaleServiceImpl extends ServiceImpl<ProductOnSaleMapper, ProductOnSale>
        implements ProductOnSaleService {
    @Cacheable(value = "productOnSales", key = "getMethodName()+'-'+#year+'-'+#month")
    @Override
    public List<String> listOnSaleDayListInSpecifiedYearAndMonth(Integer year, Integer month) {
        Date begin = DateUtil.getFirstDayByGivenYearAndMonth(year, month);
        Date end = DateUtil.getLastDayByGivenYearAndMonth(year, month);
        List<String> dates = getBaseMapper().selectListOnSaleDayInSpecifiedYearAndMonth(begin, end);
        return DateUtil.datesStringToGetDays(dates);
    }

    @Override
    public List<String> listOnSaleDayListInSpecifiedYearAndMonth(String yearMonth) {
        Integer[] yearMonthDateArray = DateUtil.dateStringSplitToYearMonthDayIntArray(yearMonth);
        Integer year = yearMonthDateArray[0];
        Integer month = yearMonthDateArray[1];
        return listOnSaleDayListInSpecifiedYearAndMonth(year, month);
    }

    @Cacheable(value = "productOnSales", key = "getMethodName()+'-'+#date")
    @Override
    public List<Integer> listIdByDate(Date date)  {
        return getBaseMapper().selectListIdBySelledDate(date);
    }

    @Cacheable(value = "productOnSales", key = "getMethodName()+'-'+#date")
    @Override
    public List<ProductOnSale> listByDate(Date date)  {
        return getBaseMapper().selectListBySelledDate(date);
    }

    @Override
    public Boolean addReservation(OrderItem orderItem, Date date)  {
        return getBaseMapper().addOnSaleProductReservation(orderItem, date);
    }

    @Autowired
    ProductService productService;

    @Override
    public BigDecimal getPriceOrNewPrice(Date date, Integer productId)  {
        ProductOnSale productOnSale = getBaseMapper().getByProductIdAndSelledDate(productId, date);
        if (productOnSale.getNewPrice() == null) {
            Product product = productService.getById(productId);
            return product.getPrice();
        }
        return productOnSale.getNewPrice();
    }

    @CacheEvict(value = "productOnSales",allEntries = true)
    @Override
    public boolean removeBatchByIdsAndDate(List<Integer> idList, Date date) {
        if (idList.size() > 0) {
            QueryWrapper<ProductOnSale> wrapper = new QueryWrapper<>();
            wrapper = wrapper.in("id", idList).eq("selled_date", date);
            return getBaseMapper().delete(wrapper) == 1;
        }
        return true;
    }

    @Override
    public ProductOnSale getByProductIdAndSelledDate(Integer productId, Date sellDate) {
        return getBaseMapper().getByProductIdAndSelledDate(productId,sellDate);
    }

    @Caching(evict = {
            @CacheEvict(value = "productOnSales",allEntries = true),
            @CacheEvict(value = "productOnSale", key = "#entity.id")
    })
    @Override
    public boolean saveOrUpdate(ProductOnSale entity) {
        return super.saveOrUpdate(entity);
    }
    @CacheEvict(value = "productOnSales",allEntries = true)
    @Override
    public boolean saveOrUpdateBatch(Collection<ProductOnSale> entityList) {
        return super.saveOrUpdateBatch(entityList);
    }
}




