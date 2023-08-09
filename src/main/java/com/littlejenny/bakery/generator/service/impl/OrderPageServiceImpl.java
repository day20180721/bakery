package com.littlejenny.bakery.generator.service.impl;

import com.littlejenny.bakery.enums.OrderStatus;
import com.littlejenny.bakery.exception.OutOfOpeningTimeException;
import com.littlejenny.bakery.generator.dto.Order;
import com.littlejenny.bakery.generator.dto.OrderItem;
import com.littlejenny.bakery.generator.service.*;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.to.OrderTO;
import com.littlejenny.bakery.to.OrderItemTO;
import com.littlejenny.bakery.utils.DateUtil;
import com.littlejenny.bakery.utils.PageUtil;
import com.littlejenny.bakery.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderPageServiceImpl implements OrderPageService {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    CartService cartService;
    @Autowired
    BakeryConfigService bakeryConfigService;

    @Transactional
    @Override
    public void saveByUserIdAndDateAndOrderTO(String userId, Date date, OrderTO orderTO) throws RuntimeException {
        if (!bakeryConfigService.isInOpeningTime(orderTO.getHour(), orderTO.getMinute()))
            throw new OutOfOpeningTimeException();
        Order order = getOrderFromUserIdAndDateAndTO(userId, date, orderTO);
        orderService.save(order);

        List<OrderItem> orderItems = getOrderItemsFromOrderAndTO(date, order, orderTO);
        orderItemService.saveBatch(orderItems);

        addOrderItemsReservationToProductOnSale(date, orderItems);

        cartService.removeCartAndItemByUserIdAndReservationDate(userId, date);
    }

    @Override
    public MyPage<OrderVO> pageByCustomerId(Long page, String customerId) {
        MyPage<Order> orderMyPage = orderService.pageByCustomerId(page, customerId);
        List<OrderVO> orderVOList = getOrderVOListFromList(orderMyPage.getRecords());
        return PageUtil.replaceRecordListWithSourcePage(orderMyPage, orderVOList);
    }

    private List<OrderVO> getOrderVOListFromList(List<Order> orderList) {
        return orderList.stream().map(item -> {
            OrderVO vo = new OrderVO();
            BeanUtils.copyProperties(item, vo);
            vo.setOrderStatusName(OrderStatus.getNameByIdOrNull(item.getOrderStatus()));
            return vo;
        }).toList();
    }

    private Order getOrderFromUserIdAndDateAndTO(String userId, Date date, OrderTO to)  {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.CREATED.getId());
        order.setNote(to.getNote());
        order.setCustomerId(userId);
        order.setTotalPrice(getOrderTotalPrice(date, to));

        Date now = new Date();
        order.setCreatedDatetime(now);
        order.setLastUpdatedDatetime(now);
        Date shippingTime = DateUtil.addHour(date, to.getHour());
        shippingTime = DateUtil.addMinute(shippingTime, to.getMinute());
        order.setShippingTime(shippingTime);
        return order;
    }

    @Autowired
    ProductOnSaleService productOnSaleService;

    private BigDecimal getOrderTotalPrice(Date date, OrderTO order)  {
        BigDecimal sum = new BigDecimal(0);
        for (OrderItemTO item : order.getItems()) {
            BigDecimal price = productOnSaleService.getPriceOrNewPrice(date, item.getProductId());
            BigDecimal count = new BigDecimal(item.getCount());
            BigDecimal totalPrice = price.multiply(count);
            sum = sum.add(totalPrice);
        }
        return sum;
    }

    private List<OrderItem> getOrderItemsFromOrderAndTO(Date date, Order order, OrderTO orderTo)  {
        List<OrderItemTO> TOList = orderTo.getItems();
        List<OrderItem> itemList = new ArrayList<>();
        for (OrderItemTO to : TOList) {
            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setProductId(to.getProductId());
            item.setCount(to.getCount());

            BigDecimal price = productOnSaleService.getPriceOrNewPrice(date, item.getProductId());
            item.setPrice(price);

            itemList.add(item);
        }
        return itemList;
    }

    private void addOrderItemsReservationToProductOnSale(Date date, List<OrderItem> items) throws RuntimeException{
        for (OrderItem item : items) {
            addOrderItemReservationToProductOnSale(date, item);
        }
    }

    private void addOrderItemReservationToProductOnSale(Date date, OrderItem item)  {
        Boolean reservationIsNotFull = productOnSaleService.addReservation(item, date);
        if (!reservationIsNotFull) {
            throw new RuntimeException("item " + item.getProductId() + "'s reservation is full " + " at " + date);
        }
    }
}
