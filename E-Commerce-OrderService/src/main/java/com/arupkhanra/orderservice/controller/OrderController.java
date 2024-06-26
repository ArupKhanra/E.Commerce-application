package com.arupkhanra.orderservice.controller;

import com.arupkhanra.orderservice.model.OrderRequest;
import com.arupkhanra.orderservice.model.OrderResponse;
import com.arupkhanra.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-service")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    //http://localhost:8082/order-service/placeOrder
    @PostMapping("/placeOrder")
    public ResponseEntity<Long>placeOrder(@RequestBody OrderRequest orderRequest){
       long orderId=orderService.placeOrder(orderRequest);
       log.info("orderId :{} "+orderId);
       return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse>getOrderDetails(@PathVariable ("orderId")long orderId){
     OrderResponse orderResponse
             =orderService.getOrderDetails(orderId);
     return new ResponseEntity<>(orderResponse,HttpStatus.OK);
    }

}
