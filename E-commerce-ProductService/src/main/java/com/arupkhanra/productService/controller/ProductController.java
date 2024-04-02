package com.arupkhanra.productService.controller;

import com.arupkhanra.productService.model.ProductRequest;
import com.arupkhanra.productService.model.ProductResponse;
import com.arupkhanra.productService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/products"})
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long>addProduct(@RequestBody ProductRequest productRequest){
        Long productId=productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse>getProductById(@PathVariable("id") long productId){
        ProductResponse productResponse=
                productService.getProductById(productId);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    //http://localhost:8080/products/reduceQuantity/2?quantity=2
    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void>reduceQuantity(
            @PathVariable("id") long productId,@RequestParam long quantity){

        productService.reduceQuantity(productId,quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
