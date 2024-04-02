package com.arupkhanra.orderservice.service;

import com.arupkhanra.orderservice.entity.Order;
import com.arupkhanra.orderservice.exception.CustomException;
import com.arupkhanra.orderservice.external.client.PaymentService;
import com.arupkhanra.orderservice.external.client.ProductResponse;
import com.arupkhanra.orderservice.external.client.ProductService;
import com.arupkhanra.orderservice.external.request.PaymentRequest;
import com.arupkhanra.orderservice.external.response.PaymentResponse;
import com.arupkhanra.orderservice.model.OrderRequest;
import com.arupkhanra.orderservice.model.OrderResponse;
import com.arupkhanra.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //order entity -> save the data with status order create
        //productService -> Block Product(Reduce the quantity)
        //payment service -> payment->success ->completed
        //canceled

        log.info("placing Order request {} "+orderRequest);
        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
        log.info("Creating order with status CREATED");
        Order order=Order.builder()

                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
            order=orderRepository.save(order);
            log.info("Calling Payment service to complete Payment");

        PaymentRequest paymentRequest=
                PaymentRequest.builder()
                        .orderId(order.getId())
                        .paymentMode(orderRequest.getPaymentMode())
                        .amount(orderRequest.getTotalAmount())
                        .build();
        String orderStatus =null;
        try{
            paymentService.doPayment(paymentRequest);
            log.info("Payment Done successFully. Changing the Order status to placed");
            orderStatus="PLACED";
        }catch(Exception e){
            log.info("Error occurred in Payment. Changing Order status  to PAYMENT_FAILED");
            orderStatus="PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

            log.info("Order place successfully with order id: {} "+order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get order details for order id: {}",orderId);
        System.out.println(orderId);
        Order order
                =orderRepository.findById(orderId)
                .orElseThrow(()->new CustomException("order not found for the order id :{}"+orderId,
                        "NOT_FOUND",404));

        log.info("Invoking Product Service to fetch the product for id: {}",order.getProductId());

        ProductResponse productResponse=restTemplate.getForObject(
                "http://PRODUCT-SERVICE/products/"
                        +order.getProductId(), ProductResponse.class
        );

        log.info("getting payment information form the Payment Service");

        PaymentResponse paymentResponse
                =restTemplate.getForObject("http://PAYMENT-SERVICE/payment/order/"+order.getId(),PaymentResponse.class);

        OrderResponse.ProductDetails productDetails
                =OrderResponse.ProductDetails
                .builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .price(productResponse.getPrice())
                .quantity(productResponse.getQuantity() )
                .build();

        OrderResponse.PaymentDetails paymentDetails
                =OrderResponse.PaymentDetails
                .builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();


        OrderResponse orderResponse=
                OrderResponse.builder()
                        .orderId(order.getId())
                        .orderStatus(order.getOrderStatus())
                        .amount(order.getAmount())
                        .orderDate(order.getOrderDate())
                        .productDetails(productDetails)
                        .paymentDetails(paymentDetails)
                        .build();

        return orderResponse;
    }


}
