package com.littlejenny.bakery.generator.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "product_on_sale",schema = "bakery")
public class ProductOnSale implements Serializable {
    @Serial
    private static final long serialVersionUID = 1664505495056685547L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "商品編號缺失")
    private Integer productId;
    @Min(value = 1,message = "數量必須大於 0")
    @NotNull(message = "商品數量不可為空")
    private Integer stock;
    @Min(value = 0,message = "預約必須至少等於 0")
    @NotNull(message = "預約數量不可為空")
    private Integer reservation;
    private BigDecimal newPrice;
    @Future(message = "預約時間必須為未來")
    @NotNull(message = "販售時間不可為空")
    private Date selledDate;

}
