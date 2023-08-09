package com.littlejenny.bakery.generator.service.impl;

import com.littlejenny.bakery.enums.OrderStatus;
import com.littlejenny.bakery.generator.dto.Order;
import com.littlejenny.bakery.generator.service.BOrderPageService;
import com.littlejenny.bakery.generator.service.OrderService;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.to.BOrderTO;
import com.littlejenny.bakery.utils.PageUtil;
import com.littlejenny.bakery.vo.BOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BOrderPageServiceImpl implements BOrderPageService {
    @Autowired
    OrderService orderService;
    @Override
    public MyPage<BOrderVO> pageBOrderVO(Long page) {
        return getVOPageFromPage(orderService.page(PageUtil.of(page)));
    }
    private MyPage<BOrderVO> getVOPageFromPage(MyPage<Order> page){
        List<BOrderVO> voList = getVOListFromOrderList(page.getRecords());
        return PageUtil.replaceRecordListWithSourcePage(page,voList);
    }
    @Override
    public boolean updateOrderStatus(BOrderTO to) {
        return orderService.updateById(getOrderFromTO(to));
    }
    public Order getOrderFromTO(BOrderTO to){
        Order order = new Order();
        order.setId(to.getOrderId());
        order.setOrderStatus(to.getOrderStatusId());
        return order;
    }

    private List<BOrderVO> getVOListFromOrderList(List<Order> orderList){
        return orderList.stream().map(item->{
            BOrderVO vo = new BOrderVO();
            BeanUtils.copyProperties(item,vo);
            String orderStatusName =  OrderStatus.getNameByIdOrNull(vo.getOrderStatus());
            vo.setOrderStatusName(orderStatusName);
            return vo;
        }).toList();
    }
}
