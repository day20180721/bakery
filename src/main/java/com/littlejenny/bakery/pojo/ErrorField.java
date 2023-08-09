package com.littlejenny.bakery.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorField {
    private String field;
    private String msg;
    public static ErrorField get(String field,Exception e){
        return new ErrorField(field,e.getMessage());
    }
}
