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


@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "category",schema = "bakery")
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = -5720572325537482498L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "必須填寫種類名")
    private String name;
    private Integer parentId;
    private Integer sortOrder;
    private String parentName;

}
