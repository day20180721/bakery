package com.littlejenny.bakery.generator.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
@TableName(value = "product",schema = "bakery")
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = -6041343324048734936L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "必須填寫商品名")
    private String name;
    @Min(message = "售價必須至少等於 0", value = 0L)
    @NotNull(message = "售價不可為空")
    private BigDecimal price;
    private String description;
    private Date createdTime;
    private Date lastUpdatedTime;
    @NotNull(message = "種類編號缺失")
    private Integer categoryId;

}
