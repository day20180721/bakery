package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.to.BProductTO;
import com.littlejenny.bakery.vo.BProductVO;

import java.util.List;

public interface BProductPageService {
    MyPage<BProductVO> pageProductVO(Long page);
    void saveOrUpdate(BProductTO productTo);
}
