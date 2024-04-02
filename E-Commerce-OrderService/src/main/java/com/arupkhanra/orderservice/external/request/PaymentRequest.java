package com.arupkhanra.orderservice.external.request;

import com.arupkhanra.orderservice.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private Long id;
    private Long orderId;
    private String referenceNumber;
    private PaymentMode paymentMode;
    private Long amount;

}
