package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.vo.OrderDetailVO;

public interface OrderDetailPageService {
    OrderDetailVO getOrderVOByOrderId(Long orderId);
}
