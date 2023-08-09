package com.littlejenny.bakery.to;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BProductTO {

    private Integer productId;
    @NotBlank(message = "名稱必須填寫")
    private String name;
    @Min(message = "售價必須至少等於 0", value = 0L)
    @NotNull(message = "售價必須填寫")
    private BigDecimal price;
    private String description;
    private String imageName;
    @NotNull(message = "種類編號缺失")
    private Integer categoryId;
}
