package org.example.magazine.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.magazine.model.OrderStatus;
import org.example.magazine.model.Product;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private Long userId;
    private List<Product> products;
    private Double totalPrice;
    private OrderStatus status;
    private LocalDate orderDate;
}
