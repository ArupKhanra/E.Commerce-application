package com.arupkhanra.productService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @Column(name = "PRODUCT_NAME",nullable = false)
    private String productName;
    @Column(name = "PRICE")
    private Long price;
    @Column(name = "QUANTITY")
    private Long quantity;
}
