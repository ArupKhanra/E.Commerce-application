package com.arupkhanra.paymentService.model;

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
