package com.littlejenny.bakery.to;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemTO {
    Long id;
    @NotNull(message = "商品編號缺失")
    Integer productId;
    @Min(value = 1,message = "購買數量必須大於 0")
    @NotNull(message = "購買數量必須填寫")
    Integer count;
}
