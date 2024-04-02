package com.arupkhanra.productService.service;

import com.arupkhanra.productService.entity.Product;
import com.arupkhanra.productService.exception.ProductServiceCustomException;
import com.arupkhanra.productService.model.ProductRequest;
import com.arupkhanra.productService.model.ProductResponse;
import com.arupkhanra.productService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("adding product successful .. ");
        Product product=Product
                .builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        log.info("product created ..");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("get the product for productId: {}"+productId);
        Product product=
                productRepository.findById(productId)
                        .orElseThrow(()->new ProductServiceCustomException("product with given id not found","PRODUCT_NOT_FOUND"));

        ProductResponse productResponse=new ProductResponse();
        BeanUtils.copyProperties(product,productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("reduce quantity {} for id {} ", productId, quantity);
        Product product
                = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException
                        ("product with id not found", "PRODUCT_NOT_FOUND"));

        if (product.getQuantity() < quantity) {
            throw new
                    ProductServiceCustomException("product dose not have sufficient quantity",
                    "INSUFFICIENT-QUANTITY");

        }

       product.setQuantity(product.getQuantity()- quantity);
       productRepository.save(product);
        log.info("product quantity updated successfully ");
    }
}
