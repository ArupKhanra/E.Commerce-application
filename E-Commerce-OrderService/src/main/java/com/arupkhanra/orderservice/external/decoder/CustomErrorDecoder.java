package com.arupkhanra.orderservice.external.decoder;

import com.arupkhanra.orderservice.exception.CustomException;
import com.arupkhanra.orderservice.external.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("::{}",response.request().url());
        log.info("::{}",response.request().headers());
        try {
            ErrorResponse errorResponse =
                    objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);

            // Check if the error is related to insufficient quantity
            if ("INSUFFICIENT-QUANTITY".equals(errorResponse.getErrorCode())) {
                return new CustomException(
                        "Product does not have sufficient quantity",
                        errorResponse.getErrorCode(),
                        response.status());
            } else {
                // Handle other errors here
                return new CustomException(
                        errorResponse.getErrorMassage(),
                        errorResponse.getErrorCode(),
                        response.status());
            }

        } catch (IOException e) {
            // Handle IO exception
            throw new CustomException("Internal Server error", "INTERNAL_SERVER_ERROR", 500);
        }
    }
}
