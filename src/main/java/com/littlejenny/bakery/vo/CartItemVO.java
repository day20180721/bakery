package com.littlejenny.bakery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItemVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1521068260936522765L;
    private Long id;
    private Integer productId;
    private Long cartId;
    private String name;
    private BigDecimal price;
    private BigDecimal newPrice;
    private Integer count;
    private String description;
    private Integer stock;
    private Integer reservation;
    private BigDecimal totalPrice;
    public BigDecimal getTotalPrice(){
        BigDecimal _count = new BigDecimal(count);
        return getPrice().multiply(_count);
    }
    public BigDecimal getPrice(){
        return newPrice == null ? price : newPrice;
    }
    public Integer getRemaining(){
        return stock - reservation;
    }
}
