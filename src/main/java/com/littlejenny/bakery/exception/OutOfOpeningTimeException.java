package com.littlejenny.bakery.exception;

import java.io.Serial;

public class OutOfOpeningTimeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7692719633157046014L;

    @Override
    public String getMessage() {
        return "超出營業時間";
    }
}
