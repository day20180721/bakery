package com.littlejenny.bakery.enums;

import lombok.Getter;

import java.util.*;

@Getter
public enum OrderStatus {
    CREATED(0, "新建"),
    CHECKED(1, "已確認"),
    CANCEL(2, "訂單取消"),
    SUCCESS(3, "訂單完成"),
    INVALID(4, "訂單已失效");
    private final int id;
    private final String name;

    OrderStatus(int code, String name) {
        this.id = code;
        this.name = name;
    }

    public static List<OrderStatus> getAll() {
        return Arrays.stream(OrderStatus.values()).toList();
    }

    private final static Map<Integer, String> cacheMap = new HashMap<>();

    public static String getNameByIdOrNull(Integer orderStatus) {
        String cache = cacheMap.get(orderStatus);
        if (cache != null) return cache;
        for (OrderStatus status : getAll()) {
            if (status.id == orderStatus) {
                cacheMap.put(orderStatus, status.name);
                return status.name;
            }
        }
        return null;
    }
}
