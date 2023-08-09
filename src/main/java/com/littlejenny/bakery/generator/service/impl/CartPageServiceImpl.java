package com.littlejenny.bakery.generator.service.impl;

import com.littlejenny.bakery.generator.dto.Cart;
import com.littlejenny.bakery.generator.dto.CartItem;
import com.littlejenny.bakery.generator.dto.Product;
import com.littlejenny.bakery.generator.dto.ProductOnSale;
import com.littlejenny.bakery.generator.service.*;
import com.littlejenny.bakery.vo.CartItemVO;
import com.littlejenny.bakery.utils.DateUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartPageServiceImpl implements CartPageService {

    @Autowired
    CartService cartService;
    @Autowired
    CartItemService cartItemService;

    @Override
    public List<CartItemVO> listCartItemVOByUserIdAndDate(String userId, Date reservationDate)  {
        Cart cart = cartService.getOrNullByUserIdAndDate(userId, reservationDate);
        if (cart == null) {
            return new ArrayList<>();
        }else {
            List<CartItem> cartItems = cartItemService.listByCartId(cart.getId());
            return getVOListFromCartItem(cartItems, reservationDate);
        }
    }

    private List<CartItemVO> getVOListFromCartItem(List<CartItem> items, Date reservationDate)  {
        return items.stream().map(item -> getCartItemVO(item, reservationDate)).toList();
    }

    private CartItemVO getCartItemVO(CartItem item, Date reservationDate) {
        CartItemVO vo = new CartItemVO();
        setCartItemPropertiesTo(vo, item);
        setProductOnSalePropertiesTo(vo, item.getProductId(), reservationDate);
        setProductPropertiesTo(vo, item.getProductId());
        return vo;
    }

    @Autowired
    ProductService productService;
    @Autowired
    ProductOnSaleService productOnSaleService;
    private void setCartItemPropertiesTo(CartItemVO vo, CartItem item) {
        BeanUtils.copyProperties(item, vo);
    }
    private void setProductOnSalePropertiesTo(CartItemVO vo, Integer productId, Date reservationDate) {
        ProductOnSale onSale = productOnSaleService.getByProductIdAndSelledDate(productId, reservationDate);
        BeanUtils.copyProperties(onSale, vo, "id");
    }
    private void setProductPropertiesTo(CartItemVO vo, Integer productId) {
        Product product = productService.getById(productId);
        BeanUtils.copyProperties(product, vo, "id");
    }
}
