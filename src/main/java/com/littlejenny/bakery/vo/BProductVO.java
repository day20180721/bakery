package com.littlejenny.bakery.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BProductVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -837959143886465247L;
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private Integer categoryId;
    private String categoryName;
}
