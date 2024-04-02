package com.arupkhanra.productService.service;

import com.arupkhanra.productService.model.ProductRequest;
import com.arupkhanra.productService.model.ProductResponse;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);
     ProductResponse getProductById(Long productId) ;

    void reduceQuantity(long productId, long quantity);
}
