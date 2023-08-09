package com.littlejenny.bakery.generator.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "cart",schema = "bakery")
public class Cart implements Serializable {
    @Serial
    private static final long serialVersionUID = 3657741699936130873L;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @Future(message = "預約時間必須為未來")
    @NotNull(message = "預約時間不可為空")
    private Date reservationDate;
    @NotNull(message = "客戶編號缺失")
    private String customerId;

}
