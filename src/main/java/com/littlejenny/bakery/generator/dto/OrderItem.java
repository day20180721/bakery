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
@TableName(value = "order_item",schema = "bakery")
public class OrderItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 7780603698163110778L;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @NotNull(message = "訂單編號缺失")
    private Long orderId;
    @NotNull(message = "商品編號缺失")
    private Integer productId;
    @Min(value = 0,message = "購買數量必須大於 0")
    @NotNull(message = "購買數量不可為空")
    private Integer count;
    @NotNull(message = "價格不可為空")
    @Min(value = 0, message = "金額至少為0")
    private BigDecimal price;
}
