package com.littlejenny.bakery.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class BakeryConfigMapper {
    @Autowired
    com.littlejenny.bakery.generator.mapper.BakeryConfigMapper bakeryConfigMapper;
    @Test
    public void getOpenTime(){
        Integer openTime = bakeryConfigMapper.selectOpenTime();
        System.out.println(openTime);
        Assert.notNull(openTime,"SQL有誤");
    }
    @Test
    public void setOpenTime(){
        boolean result = bakeryConfigMapper.updateOpenTime(50);
        Assert.isTrue(result,"SQL有誤");
    }
}
