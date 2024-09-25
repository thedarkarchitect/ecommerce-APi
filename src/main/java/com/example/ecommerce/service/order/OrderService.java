package com.example.ecommerce.service.order;

import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.models.Order;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);
    List<OrderDTO> getUserOrders(Long userId);
}
