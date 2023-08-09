package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.generator.dto.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author user
* @description 针对表【product】的数据库操作Service
* @createDate 2023-07-11 16:57:42
*/
public interface ProductService extends IService<Product> {
    List<Product> listAndExcludeById(List<Integer> idList);
}
