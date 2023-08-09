package com.littlejenny.bakery.generator.service.impl;

import com.littlejenny.bakery.generator.dto.Cart;
import com.littlejenny.bakery.generator.dto.CartItem;
import com.littlejenny.bakery.generator.service.CartItemPageService;
import com.littlejenny.bakery.generator.service.CartItemService;
import com.littlejenny.bakery.generator.service.CartService;
import com.littlejenny.bakery.to.CartItemRemoveTO;
import com.littlejenny.bakery.to.CartItemTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartItemPageServiceImpl implements CartItemPageService {
    @Autowired
    CartService cartService;
    @Override
    public boolean save(String userId, Date reservationDate, CartItemTO item)  {
        Cart cart = cartService.getOrNullByUserIdAndDate(userId, reservationDate);
        if (cart == null) {
            cart = cartService.newAndSave(userId, reservationDate);
            return newCartItemAndSave(cart.getId(), item);
        } else {
            return newCartItemOrModifyExistCartItemAndSave(cart.getId(), item);
        }
    }

    @Autowired
    CartItemService cartItemService;

    private boolean newCartItemOrModifyExistCartItemAndSave(Long cartId, CartItemTO item) {
        CartItem existItem = cartItemService.getByCartIdAndProductId(cartId, item.getProductId());
        if (existItem == null) {
            return newCartItemAndSave(cartId, item);
        } else {
            return modifyExistCartItemCount(existItem, item.getCount());
        }
    }

    private boolean newCartItemAndSave(Long cartId, CartItemTO item) {
        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartId);
        cartItem.setProductId(item.getProductId());
        cartItem.setCount(item.getCount());
        return cartItemService.save(cartItem);
    }

    private boolean modifyExistCartItemCount(CartItem existItem, Integer modification) {
        existItem.setCount(existItem.getCount() + modification);
        return cartItemService.updateById(existItem);
    }

    @Override
    public boolean update(String userId, Date date, CartItemTO item)  {
        Cart cart = cartService.getOrNullByUserIdAndDate(userId, date);
        if (cart != null) {
            CartItem cartItem = cartItemService.getByCartIdAndProductId(cart.getId(), item.getProductId());
            cartItem.setCount(item.getCount());
            return cartItemService.updateById(cartItem);
        }
        return false;
    }

    @Override
    public boolean remove(CartItemRemoveTO cartItemDeleteTO) {
        Long cartItemId = cartItemDeleteTO.getId();
        Long cartId = cartItemDeleteTO.getCartId();
        boolean result = cartItemService.removeById(cartItemId);
        if(cartItemService.listByCartId(cartId).isEmpty()){
            result = cartService.removeById(cartId);
        }
        return result;
    }
}
