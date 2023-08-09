package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.generator.service.OrderDetailPageService;
import com.littlejenny.bakery.vo.OrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${customer-prefix-value}/orderDetail")
@Validated
public class OrderDetailController {
    @Autowired
    OrderDetailPageService orderDetailPageService;
    @GetMapping("/{orderId}")
    public String selectWithOrderId(@PathVariable("orderId")Long orderId, Model model){
        addOrderVOToModel(orderId,model);
        return "order-detail";
    }
    public void addOrderVOToModel(Long orderId,Model model){
        OrderDetailVO orderVO = orderDetailPageService.getOrderVOByOrderId(orderId);
        model.addAttribute("orderDetailVO",orderVO);
    }
}
