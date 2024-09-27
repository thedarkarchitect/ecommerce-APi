package com.example.ecommerce.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse { // Response object to send back to the client
    private String message;
    private Object data;
}
