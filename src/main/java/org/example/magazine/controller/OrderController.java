package org.example.magazine.controller;

import org.example.magazine.controller.dto.OrderRequest;
import org.example.magazine.controller.dto.OrderResponse;
import org.example.magazine.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> add(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.save(orderRequest);
        return ResponseEntity.ok(orderResponse);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}
