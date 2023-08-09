package com.littlejenny.bakery.advice;

import com.littlejenny.bakery.controller.BCategoryController;
import com.littlejenny.bakery.pojo.ErrorMap;
import com.littlejenny.bakery.pojo.R;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(basePackageClasses = BCategoryController.class)
public class SQLIntegrityConstraintViolationHandle {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public R handle(SQLIntegrityConstraintViolationException exception) {
        return R.error("Update/Remove時的外鍵關聯錯誤");
    }
}
