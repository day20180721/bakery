package com.littlejenny.bakery.advice;

import com.littlejenny.bakery.pojo.ErrorMap;
import com.littlejenny.bakery.pojo.R;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@ControllerAdvice(annotations = Controller.class)
public class ConstraintViolationHandle {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public R handle(ConstraintViolationException constraintViolationException) {

        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        if (violations.isEmpty()) {
            return R.error;
        } else {
            return R.error(ErrorMap.getErrorMap(constraintViolationException));
        }
    }
}
