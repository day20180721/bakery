package com.littlejenny.bakery.generator.service.impl;

import com.littlejenny.bakery.generator.dto.Product;
import com.littlejenny.bakery.generator.dto.ProductOnSale;
import com.littlejenny.bakery.generator.service.BShopPageService;
import com.littlejenny.bakery.generator.service.ProductOnSaleService;
import com.littlejenny.bakery.generator.service.ProductService;
import com.littlejenny.bakery.to.BShopProductTO;
import com.littlejenny.bakery.vo.BShopProductVO;
import com.littlejenny.bakery.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BShopPageServiceImpl implements BShopPageService {
    @Override
    public List<BShopProductVO> listOnSaleProductByDate(Date date) {
        List<ProductOnSale> onSaleProductList = productOnSaleService.listByDate(date);
        return listVOFromProductOnSaleList(onSaleProductList);
    }

    private List<BShopProductVO> listVOFromProductOnSaleList(List<ProductOnSale> productOnSaleList) {
        return productOnSaleList.stream().map(this::getVOFromProductOnSale).toList();
    }

    private BShopProductVO getVOFromProductOnSale(ProductOnSale productOnSale) {
        BShopProductVO vo = new BShopProductVO();
        Integer productId = productOnSale.getProductId();
        Product product = productService.getById(productId);
        BeanUtils.copyProperties(product, vo);
        BeanUtils.copyProperties(productOnSale, vo);
        return vo;
    }

    @Autowired
    private ProductOnSaleService productOnSaleService;
    @Autowired
    private ProductService productService;

    @Override
    public List<Product> listNotOnSaleProductByDate(Date date) {
        List<Integer> idList = productOnSaleService.listIdByDate(date);
        return productService.listAndExcludeById(idList);
    }

    @Override
    public Boolean saveOrUpdateBatch(List<BShopProductTO> products, Date date) {
        return productOnSaleService.saveOrUpdateBatch(getProductOnSaleListFromTOAndDate(products, date));
    }

    private List<ProductOnSale> getProductOnSaleListFromTOAndDate(List<BShopProductTO> products, Date date) {
        return products.stream().map(item -> getProductOnSaleFromTOAndDate(item, date)).toList();
    }

    private ProductOnSale getProductOnSaleFromTOAndDate(BShopProductTO to, Date date) {
        ProductOnSale productOnSale = new ProductOnSale();
        BeanUtils.copyProperties(to, productOnSale);
        productOnSale.setId(to.getProductOnSaleId());
        productOnSale.setSelledDate(date);
        return productOnSale;
    }
}
