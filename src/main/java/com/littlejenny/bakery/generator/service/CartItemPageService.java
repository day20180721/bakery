package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.to.CartItemRemoveTO;
import com.littlejenny.bakery.to.CartItemTO;

import java.text.ParseException;
import java.util.Date;

public interface CartItemPageService {
    boolean save(String userId, Date date, CartItemTO item) throws ParseException;
    boolean update(String userId, Date date, CartItemTO item) throws ParseException;

    boolean remove(CartItemRemoveTO cartItemDeleteTO);
}
