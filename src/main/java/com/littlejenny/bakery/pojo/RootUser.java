package com.littlejenny.bakery.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class RootUser implements Serializable {
    @Serial
    private static final long serialVersionUID = -3970932874986779815L;
}
