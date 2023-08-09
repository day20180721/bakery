package com.littlejenny.bakery.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.littlejenny.bakery.generator.dto.CartItem;
import com.littlejenny.bakery.generator.service.CartItemService;
import com.littlejenny.bakery.generator.mapper.CartItemMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author user
 * @description 针对表【cart_item】的数据库操作Service实现
 * @createDate 2023-07-13 23:06:38
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem>
        implements CartItemService {
    @Cacheable(value = "cartItems", key = "getMethodName()+'-'+#cartId")
    @Override
    public List<CartItem> listByCartId(Long cartId) {
        return getBaseMapper().selectListByCartId(cartId);
    }

    @Override
    public CartItem getByCartIdAndProductId(Long id, Integer productId) {
        return getBaseMapper().getByCartIdAndProductId(id, productId);
    }
    @Override
    public boolean removeByCartId(Long cartId) {
        return getBaseMapper().deleteByCartId(cartId);
    }
    @CacheEvict(value = "cartItems", allEntries = true)
    @Override
    public boolean save(CartItem entity) {
        return super.save(entity);
    }

    @CacheEvict(value = "cartItems", allEntries = true)
    @Override
    public boolean updateById(CartItem entity) {
        return super.updateById(entity);
    }

    @CacheEvict(value = "cartItems", allEntries = true)
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}




