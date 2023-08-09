package com.littlejenny.bakery.generator.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
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
@TableName(value = "order",schema = "bakery")
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = -2464662886415428514L;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @NotBlank(message = "客戶編號缺失")
    private String customerId;
    private String note;
    private BigDecimal totalPrice;
    private Date createdDatetime;
    private Date lastUpdatedDatetime;
    private Integer orderStatus;
    private Date shippingTime;

}
