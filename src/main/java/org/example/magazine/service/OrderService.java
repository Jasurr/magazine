package org.example.magazine.service;

import org.example.magazine.controller.dto.OrderRequest;
import org.example.magazine.controller.dto.OrderResponse;
import org.example.magazine.model.AppUser;
import org.example.magazine.model.Order;
import org.example.magazine.model.OrderStatus;
import org.example.magazine.model.Product;
import org.example.magazine.repository.OrderRepository;
import org.example.magazine.repository.UserRepository;
import org.example.magazine.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    final OrderRepository orderRepository;
    final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAll() {
        String userName = UserUtil.getUserName();
        Order order = orderRepository.getOrdersByAppUser_Username(userName).get();
        OrderResponse response = new OrderResponse();
        response.setOrderDate(order.getOrderDate());
        response.setProducts(order.getProducts());
        response.setId(order.getId());
        response.setUserId(order.getAppUser().getId());
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus());
        List<OrderResponse> list = new ArrayList<>();
        list.add(response);
        return list;
    }

    public OrderResponse save(OrderRequest orderRequest) {
        String userName = UserUtil.getUserName();
        Optional<Order> oldOrder = orderRepository.getOrdersByAppUser_Username(userName);
        if (oldOrder.isPresent()) {
            delete(oldOrder.get().getId());
        }

        Order order = new Order();
        order.setProducts(orderRequest.getProducts());
        order.setStatus(OrderStatus.PENDING);

        AppUser appUser = userRepository.findByUsername(userName).get();
        order.setAppUser(appUser);
        Optional<Double> totalPrice = orderRequest
                .getProducts()
                .stream()
                .map(Product::getPrice)
                .reduce(Double::sum);
        order.setTotalPrice(totalPrice.get());
        orderRepository.save(order);
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setProducts(order.getProducts());
        response.setStatus(order.getStatus());
        response.setUserId(order.getAppUser().getId());
        response.setTotalPrice(order.getTotalPrice());

        return response;
    }


    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
