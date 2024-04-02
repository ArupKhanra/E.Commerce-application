package com.arupkhanra.productService.model;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private Long price;
    private Long quantity;


}
