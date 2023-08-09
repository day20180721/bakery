package com.littlejenny.bakery.pojo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

public class ErrorMap {
    public static HashMap<String, String> getErrorMap(List<ErrorField> errorFields) {
        HashMap<String, String> map = new HashMap<>();
        for (ErrorField field : errorFields) {
            map.put(field.getField(), field.getMsg());
        }
        return map;
    }
    public static HashMap<String, String> getErrorMap(String field,String msg){
        HashMap<String, String> map = new HashMap<>();
        map.put(field,msg);
        return map;
    }
    public static Map<String, String> getErrorMap(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    public static Map<String, String> getErrorMap(ConstraintViolationException constraintViolationException) {
        HashMap<String, String> map = new HashMap<>();
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        violations.forEach(item ->{
            String message = item.getMessage();
            String fieldName = "";
            Iterator<Path.Node> iterator = item.getPropertyPath().iterator();
            while (iterator.hasNext()){
                Path.Node next = iterator.next();
                if(!iterator.hasNext()){
                    fieldName = next.getName();
                }
            }
            map.put(fieldName,message);
        });
        return map;
    }
}
