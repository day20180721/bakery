package com.littlejenny.bakery.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderVO {
    private Long id;
    private BigDecimal totalPrice;
    private String orderStatusName;
    private Date shippingTime;
}
