package com.littlejenny.bakery.to;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTO {
    @NotEmpty(message = "購買的物品必須大於 0")
    @NotNull(message = "購買物品不可為空")
    private List<OrderItemTO> items;
    private String note;
    @NotNull
    private Integer hour;
    @NotNull
    private Integer minute;
}
