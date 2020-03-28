package com.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommenResult<T> {
    private int code;
    private String message;
    private T data;

    public CommenResult(int code, String message) {
        this(code,message,null);
    }
}
