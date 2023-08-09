package com.littlejenny.bakery.to;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BShopProductTO {
    private Integer productOnSaleId;
    @NotNull
    private Integer productId;
    @Min(value = 1,message = "數量必須大於 0")
    @NotNull(message = "商品數量必須填寫")
    private Integer stock;
    @Min(value = 0,message = "預約必須至少等於 0")
    @NotNull(message = "預約數量必須填寫")
    private Integer reservation;
    private BigDecimal price;
    @Min(message = "售價必須至少等於 0", value = 0L)
    private BigDecimal newPrice;
}
