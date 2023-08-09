package com.littlejenny.bakery.generator.mapper;
import org.apache.ibatis.annotations.Param;

import com.littlejenny.bakery.generator.dto.ProductGallery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author user
* @description 针对表【product_gallery】的数据库操作Mapper
* @createDate 2023-07-11 16:57:45
* @Entity com.littlejenny.bakery.generator.entity.ProductGallery
*/
public interface ProductGalleryMapper extends BaseMapper<ProductGallery> {
    ProductGallery getByProductId(@Param("productId") Integer productId);
}




