package com.littlejenny.bakery.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BOrderVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8547835698203327702L;
    private Long id;
    private String customerId;
    private String note;
    private BigDecimal totalPrice;
    private Integer orderStatus;
    private String orderStatusName;
    private Date createdDatetime;
    private Date lastUpdatedDatetime;
    private Date shippingTime;
}
