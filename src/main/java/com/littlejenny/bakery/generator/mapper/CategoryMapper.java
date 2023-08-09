package com.littlejenny.bakery.generator.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.littlejenny.bakery.generator.dto.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.littlejenny.bakery.pojo.MyPage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author user
* @description 针对表【category】的数据库操作Mapper
* @createDate 2023-07-11 16:57:21
* @Entity com.littlejenny.bakery.generator.entity.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {
    int insertWithLargestOrder(@Param("category") Category category);
    boolean updateBatchSortOrder(@Param("categoryList")List<Category> categoryList);
    MyPage<Category> listOrderBySortOrderAscWithPage(@Param("page")IPage page);

    List<Category> listByDateAndSortOrderAsc(@Param("date") Date date);
}




