package com.littlejenny.bakery.to;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BOrderTO {
    @NotNull(message = "訂單編號不可為空")
    private Long orderId;
    @NotNull
    @Min(value = 0,message = "訂單狀態編號不符合規範")
    @Max(value = 4,message = "訂單狀態編號不符合規範")
    private Integer orderStatusId;
}
