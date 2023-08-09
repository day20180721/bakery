package com.littlejenny.bakery.generator.mapper;

import com.littlejenny.bakery.vo.BShopProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface BShopPageMapper {
    List<BShopProductVO> selectListBShopProductVOByselledDate(@Param("date") Date selledDate);
}
