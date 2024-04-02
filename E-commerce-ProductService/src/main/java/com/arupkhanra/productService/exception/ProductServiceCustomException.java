package com.arupkhanra.productService.exception;

import lombok.Data;

@Data
public class ProductServiceCustomException extends RuntimeException{

    private String errorCode;

    public ProductServiceCustomException(String massage,String errorCode){
        super(massage);
        this.errorCode=errorCode;
    }
}
