package com.arupkhanra.orderservice.service;

import com.arupkhanra.orderservice.model.OrderRequest;
import com.arupkhanra.orderservice.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);


}
