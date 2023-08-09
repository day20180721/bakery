package com.littlejenny.bakery.generator.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "cart_item", schema = "bakery")
public class CartItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1343197434107826204L;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @NotNull(message = "購物車編號缺失")
    private Long cartId;
    @NotNull(message = "商品編號缺失")
    private Integer productId;
    @Min(value = 0, message = "購買數量必須大於 0")
    @NotNull(message = "購買數量不可為空")
    private Integer count;


}
