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
public class ShopProductVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1130641991173071953L;
    private String name;
    private Integer productId;
    private Integer reservation;
    private Integer stock;
    private BigDecimal price;
    private BigDecimal newPrice;
    private String description;
    private String imageUrl;
    public Integer getRemaining(){
        return stock - reservation;
    }
    public BigDecimal getPrice(){
        return newPrice == null ? price : newPrice;
    }
}
