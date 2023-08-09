package com.littlejenny.bakery.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class BakeryConfigService {
    @Autowired
    com.littlejenny.bakery.generator.service.BakeryConfigService bakeryConfigService;
    @Test
    public void setOpenTime(){
        bakeryConfigService.updateOpenTime(10);
    }
}
