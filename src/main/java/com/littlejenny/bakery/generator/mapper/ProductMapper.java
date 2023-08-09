package com.littlejenny.bakery.generator.mapper;

import com.littlejenny.bakery.generator.dto.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author user
* @description 针对表【product】的数据库操作Mapper
* @createDate 2023-07-11 16:57:42
* @Entity com.littlejenny.bakery.generator.entity.Product
*/
public interface ProductMapper extends BaseMapper<Product> {
    List<Product> listAndExcludeById(@Param("idList") List<Integer> idList);
}




