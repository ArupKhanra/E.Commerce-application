package com.arupkhanra.paymentService.controller;

import com.arupkhanra.paymentService.model.PaymentRequest;
import com.arupkhanra.paymentService.model.PaymentResponse;
import com.arupkhanra.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest), HttpStatus.OK
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse>getPaymentDetailsByOrderId(@PathVariable("orderId")long orderId){

        return new ResponseEntity<>(
                paymentService.getPaymentDetailsByOrderId(orderId),HttpStatus.OK);
    }
}
