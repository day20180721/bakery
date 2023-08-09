package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.generator.dto.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.littlejenny.bakery.pojo.MyPage;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
* @author user
* @description 针对表【category】的数据库操作Service
* @createDate 2023-07-11 16:57:21
*/
public interface CategoryService extends IService<Category> {
    boolean saveWithLargestOrderOrUpdate(Category category);
    List<Category> listOrderBySortOrderAsc();
    MyPage<Category> pageOrderBySortOrderAsc(Long page);
    List<Category> listByDate(Date date) throws ParseException;
}
