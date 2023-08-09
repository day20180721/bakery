package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.to.BOrderTO;
import com.littlejenny.bakery.vo.BOrderVO;

import java.util.List;

public interface BOrderPageService {
    MyPage<BOrderVO> pageBOrderVO(Long selectedPage);

    boolean updateOrderStatus(BOrderTO to);
}
