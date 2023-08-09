package com.littlejenny.bakery.generator.service.impl;

import com.littlejenny.bakery.enums.OrderStatus;
import com.littlejenny.bakery.generator.dto.Order;
import com.littlejenny.bakery.generator.dto.OrderItem;
import com.littlejenny.bakery.generator.dto.Product;
import com.littlejenny.bakery.generator.service.*;
import com.littlejenny.bakery.vo.OrderDetailItemVO;
import com.littlejenny.bakery.vo.OrderDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailPageServiceImpl implements OrderDetailPageService {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @Override
    public OrderDetailVO getOrderVOByOrderId(Long orderId) {
        Order order = orderService.getById(orderId);
        List<OrderItem> orderItemList = orderItemService.getByOrderId(order.getId());
        List<OrderDetailItemVO> orderItemVOList = getOrderItemVOListFromList( orderItemList);
        return getOrderVOByItemVOListAndOrder(orderItemVOList, order);
    }


    private List<OrderDetailItemVO> getOrderItemVOListFromList(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(this::getOrderItemVOFromItem).toList();
    }

    @Autowired
    private ProductService productService;

    private OrderDetailItemVO getOrderItemVOFromItem(OrderItem orderItem) {
        OrderDetailItemVO vo = new OrderDetailItemVO();

        Product product = productService.getById(orderItem.getProductId());
        BeanUtils.copyProperties(product, vo);

        BeanUtils.copyProperties(orderItem, vo);

        return vo;
    }
    private OrderDetailVO getOrderVOByItemVOListAndOrder(List<OrderDetailItemVO> orderItemVOList, Order order) {
        OrderDetailVO vo = new OrderDetailVO();
        BeanUtils.copyProperties(order, vo);
        String orderStatusName = OrderStatus.getNameByIdOrNull(order.getOrderStatus());
        vo.setOrderStatusName(orderStatusName);
        vo.setOrderDetailItemVOList(orderItemVOList);
        return vo;
    }
}
