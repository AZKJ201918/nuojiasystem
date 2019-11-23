package com.shopping.commons.exception;

import lombok.Data;

@Data
public class SuperMarketException extends Exception {

    private Integer code;

    public SuperMarketException(String message) {
        super(message);
    }


    public int getStatusCode() {
        return 500;
    }
}
