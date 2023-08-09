package com.littlejenny.bakery.generator.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.vo.ShopProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ShopPageMapper {
    MyPage<ShopProductVO> selectPageShopProductVOByDateAndCategoryId(@Param("date") Date date, @Param("categoryId")Integer categoryId, IPage<ShopProductVO> page);
    MyPage<ShopProductVO> selectPageShopProductVOByDate(@Param("date") Date date, IPage<ShopProductVO> page);

}
