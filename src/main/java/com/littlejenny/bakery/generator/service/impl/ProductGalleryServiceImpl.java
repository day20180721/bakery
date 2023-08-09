package com.littlejenny.bakery.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.littlejenny.bakery.generator.dto.ProductGallery;
import com.littlejenny.bakery.generator.service.ProductGalleryService;
import com.littlejenny.bakery.generator.mapper.ProductGalleryMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
* @author user
* @description 针对表【product_gallery】的数据库操作Service实现
* @createDate 2023-07-11 16:57:45
*/
@Service
public class ProductGalleryServiceImpl extends ServiceImpl<ProductGalleryMapper, ProductGallery>
    implements ProductGalleryService{
    @CacheEvict(value = "productGallery",key = "#entity.productId")
    @Override
    public boolean saveOrUpdate(ProductGallery entity) {
        return super.saveOrUpdate(entity);
    }
    @Cacheable(value = "productGallery",key = "#id")
    @Override
    public ProductGallery getById(Serializable id) {
        return super.getById(id);
    }
}




