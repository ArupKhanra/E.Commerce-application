package com.arupkhanra.paymentService.repository;

import com.arupkhanra.paymentService.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,Long> {

    TransactionDetails findByOrderId(long orderId);
}
