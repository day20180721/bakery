package com.littlejenny.bakery.generator.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.vo.BProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BProductPageMapper {
    MyPage<BProductVO> selectListBProductVOWithPage(IPage<BProductVO> page);
}
