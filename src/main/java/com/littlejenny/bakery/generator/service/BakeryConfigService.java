package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.exception.OutOfOpeningTimeException;
import com.littlejenny.bakery.exception.TimeFormatException;

public interface BakeryConfigService {
    boolean isInOpeningTime(Integer hours, Integer minute) throws TimeFormatException;

    Integer getOpenTime();

    boolean updateOpenTime(Integer time);

    Integer getCloseTime();

    boolean updateCloseTime(Integer time);

    Integer getReservationDateLine();

    boolean updateReservationDateLine(Integer deadLine);
}
