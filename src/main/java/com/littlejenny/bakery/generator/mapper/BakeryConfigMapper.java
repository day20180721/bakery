package com.littlejenny.bakery.generator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BakeryConfigMapper {
    Integer selectOpenTime();
    boolean updateOpenTime(@Param("newTime") Integer time);
    Integer selectCloseTime();
    boolean updateCloseTime(@Param("newTime") Integer close);
    Integer selectReservationDateLine();
    boolean updateReservationDateLine(@Param("newTime") Integer deadLine);
}
