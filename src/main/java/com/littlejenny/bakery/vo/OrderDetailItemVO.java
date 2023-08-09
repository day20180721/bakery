package com.littlejenny.bakery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailItemVO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer count;
    private String description;
    public BigDecimal getTotalPrice(){
        return price.multiply(new BigDecimal(count));
    }
}
