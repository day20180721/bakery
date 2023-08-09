package com.littlejenny.bakery.to;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRemoveTO {
    @NotNull(message = "購物車編號缺失")
    Long cartId;
    @NotNull(message = "購物商品編號缺失")
    Long id;
}
