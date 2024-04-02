package com.arupkhanra.paymentService.service;

import com.arupkhanra.paymentService.model.PaymentRequest;
import com.arupkhanra.paymentService.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
