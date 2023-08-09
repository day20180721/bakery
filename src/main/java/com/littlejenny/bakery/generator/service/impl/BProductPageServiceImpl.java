package com.littlejenny.bakery.generator.service.impl;

import com.littlejenny.bakery.generator.dto.Category;
import com.littlejenny.bakery.generator.dto.Product;
import com.littlejenny.bakery.generator.dto.ProductGallery;
import com.littlejenny.bakery.generator.service.BProductPageService;
import com.littlejenny.bakery.generator.service.CategoryService;
import com.littlejenny.bakery.generator.service.ProductGalleryService;
import com.littlejenny.bakery.generator.service.ProductService;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.utils.DateUtil;
import com.littlejenny.bakery.to.BProductTO;
import com.littlejenny.bakery.utils.PageUtil;
import com.littlejenny.bakery.vo.BProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BProductPageServiceImpl implements BProductPageService {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Override
    public MyPage<BProductVO> pageProductVO(Long page) {
        MyPage<Product> productPage = productService.page(PageUtil.of(page));
        return pageBProductVOFromPageProduct(productPage);
    };
    private MyPage<BProductVO> pageBProductVOFromPageProduct(MyPage<Product> productPage){
        List<Product> productList = productPage.getRecords();
        List<BProductVO> bProductVOList = productList.stream().map(this::getVOFromProduct).toList();
        return PageUtil.replaceRecordListWithSourcePage(productPage,bProductVOList);
    };
    @Autowired
    ProductGalleryService productGalleryService;
    private BProductVO getVOFromProduct(Product product){
        BProductVO vo = new BProductVO();
        BeanUtils.copyProperties(product,vo);

        Integer productId = product.getId();
        ProductGallery productGallery = productGalleryService.getById(productId);
        if(productGallery != null)vo.setImageUrl(productGallery.getUrl());

        Integer categoryId = product.getCategoryId();
        Category category = categoryService.getById(categoryId);
        vo.setCategoryName(category.getName());
        return vo;
    }
    @Transactional
    @Override
    public void saveOrUpdate(BProductTO productTo) {
        Product product = getProductFromBProductTO(productTo);
        productService.saveOrUpdate(product);
        saveOrUpdateOrDoNothingProductGallery(product, productTo);
    }

    private Product getProductFromBProductTO(BProductTO productTo) {
        Product product = new Product();
        BeanUtils.copyProperties(productTo, product);
        product.setId(productTo.getProductId());
        product.setCreatedTime(DateUtil.now());
        product.setLastUpdatedTime(DateUtil.now());
        return product;
    }

    @Value("${file.request-path}")
    private String fileRequestPath;
    private void saveOrUpdateOrDoNothingProductGallery(Product product, BProductTO productTo) {
        ProductGallery gallery = getProductGalleryOrNullFromProductAndTO(product, productTo);
        if (gallery != null) productGalleryService.saveOrUpdate(gallery);
    }

    private ProductGallery getProductGalleryOrNullFromProductAndTO(Product product, BProductTO productTo) {
        if (StringUtils.hasLength(productTo.getImageName())) {
            ProductGallery productGallery = new ProductGallery();

            productGallery.setProductId(product.getId());

            String imageName = productTo.getImageName();
            productGallery.setName(imageName);

            String imageUrl = fileRequestPath + "/" + imageName;
            productGallery.setUrl(imageUrl);

            return productGallery;
        }
        return null;
    }
}
