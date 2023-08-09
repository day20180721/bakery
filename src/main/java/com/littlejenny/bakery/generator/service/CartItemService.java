package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.generator.dto.CartItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author user
* @description 针对表【cart_item】的数据库操作Service
* @createDate 2023-07-13 23:06:38
*/
public interface CartItemService extends IService<CartItem> {
    List<CartItem> listByCartId(Long cartId);
    CartItem getByCartIdAndProductId(Long id, Integer productId);
    boolean removeByCartId(Long cartId);
}
