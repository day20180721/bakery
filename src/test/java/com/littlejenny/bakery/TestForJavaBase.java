package com.littlejenny.bakery;

import com.littlejenny.bakery.generator.dto.Product;
import com.littlejenny.bakery.vo.CartItemVO;
import com.littlejenny.bakery.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class TestForJavaBase {
    @Test
    public void hashmap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.putIfAbsent("5", 3);
        System.out.print(map.putIfAbsent("5", 3));
    }

    @Test
    public void calendar() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT-12"));
        calendar.set(2023, 5, 10);
        System.out.println(calendar.getTime());
    }

    @Test
    public void date() {
        System.out.println(DateUtil.nowMonth());
    }

    @Test
    public void stream() {
        List<String> strings = new ArrayList<>();
        strings.add("1.jpg");
        strings.add("2.mov");
        strings.add("3.png");

        System.out.println(strings.stream().filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , ")));

    }

    @Test
    public void arrayAsList() {
        Integer[] abc = new Integer[]{1, 2, 3};
        List<Integer> list = Arrays.asList(abc);
        list.forEach(item -> {
            System.out.println(item);
        });
    }
    @Test
    public void setAndget(){
        Product p = new Product();
        p.setPrice(new BigDecimal(250));
        CartItemVO cartItemVO = new CartItemVO();
        cartItemVO.setCount(5);
        BeanUtils.copyProperties(p,cartItemVO);
        System.out.println(cartItemVO);
    }
    @Test
    public void uuid(){
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID().variant());
        System.out.println(UUID.randomUUID().version());
    }
    @Test
    public void addHour(){
        Date date = new Date();
        System.out.println(date);
        System.out.println(DateUtil.addMinute(date,80));
    }
}
