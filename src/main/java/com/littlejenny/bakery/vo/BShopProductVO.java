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
public class BShopProductVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5841412092411822472L;
    private Integer id;
    private Integer productId;
    private Integer stock;
    private Integer reservation;
    private BigDecimal price;
    private BigDecimal newPrice;
    private String name;
    private String description;
}
