package com.littlejenny.bakery.to;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RootLoginTO {
    @NotBlank(message = "請輸入帳號")
    private String account;
    @NotBlank(message = "請輸入密碼")
    private String password;
}
