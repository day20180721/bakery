package com.littlejenny.bakery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVO {
    private List<OrderDetailItemVO> orderDetailItemVOList;
    private Long id;
    private String customerId;
    private String note;
    private BigDecimal totalPrice;
    private Date createdDatetime;
    private Date shippingTime;
    private String orderStatusName;
}
