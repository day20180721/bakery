package com.littlejenny.bakery.generator.service.impl;

import com.littlejenny.bakery.exception.TimeFormatException;
import com.littlejenny.bakery.generator.mapper.BakeryConfigMapper;
import com.littlejenny.bakery.generator.service.BakeryConfigService;
import com.littlejenny.bakery.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BakeryConfigServiceImpl implements BakeryConfigService {
    @Autowired
    BakeryConfigMapper bakeryConfigMapper;

    @Override
    public boolean isInOpeningTime(Integer hours, Integer minute) throws TimeFormatException {
        if (isHourFormatError(hours) || isMinuteFormatError(minute)) {
            throw new TimeFormatException();
        }
        Integer openTime = getOpenTime();
        Date current = new Date();
        Date openDate = DateUtil.addHour(current, openTime);
        Integer closeTime = getCloseTime();
        Date closeDate = DateUtil.addHour(current, closeTime);
        Date requestDate = DateUtil.addMinute(DateUtil.addHour(current, hours), minute);


        return DateUtil.between(requestDate,openDate,closeDate);
    }

    private boolean isHourFormatError(Integer hours) {
        return hours >= 24 || hours < 0;
    }

    private boolean isMinuteFormatError(Integer minute) {
        return minute >= 60 || minute < 0;
    }

    @Override
    public Integer getOpenTime() {
        return bakeryConfigMapper.selectOpenTime();
    }

    @Override
    public boolean updateOpenTime(Integer time) throws TimeFormatException {
        if (isHourFormatError(time)) {
            throw new TimeFormatException();
        }
        return bakeryConfigMapper.updateOpenTime(time);
    }

    @Override
    public Integer getCloseTime() {
        return bakeryConfigMapper.selectCloseTime();
    }

    @Override
    public boolean updateCloseTime(Integer time) throws TimeFormatException {
        if (isHourFormatError(time)) throw new TimeFormatException();
        return bakeryConfigMapper.updateCloseTime(time);
    }

    @Override
    public Integer getReservationDateLine() {
        return bakeryConfigMapper.selectReservationDateLine();
    }

    @Override
    public boolean updateReservationDateLine(Integer deadLine) throws RuntimeException {
        if (isReservationDateLineError(deadLine)) throw new RuntimeException("最慢預約不可小於當日");
        return bakeryConfigMapper.updateReservationDateLine(deadLine);
    }

    private boolean isReservationDateLineError(Integer deadLine) {
        return deadLine < 0;
    }
}
