package com.littlejenny.bakery.exception;

import java.io.Serial;

public class TimeFormatException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -226709135702062504L;

    @Override
    public String getMessage() {
        return "時間格式有誤";
    }
}
