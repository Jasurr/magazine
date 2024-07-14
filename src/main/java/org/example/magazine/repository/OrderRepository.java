package org.example.magazine.repository;

import org.example.magazine.model.AppUser;
import org.example.magazine.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> getOrdersByAppUser_Username(String username);


}
