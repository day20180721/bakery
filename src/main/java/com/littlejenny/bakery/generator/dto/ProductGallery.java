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
@TableName(value = "product_gallery",schema = "bakery")
public class ProductGallery implements Serializable {
    @Serial
    private static final long serialVersionUID = 8917677780642213815L;
    @TableId(type = IdType.INPUT)
    private Integer productId;
    @NotBlank(message = "必須填寫圖片名稱")
    private String name;
    @NotBlank(message = "必須填寫圖片網址")
    private String url;
}
