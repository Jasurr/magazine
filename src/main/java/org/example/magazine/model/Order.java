package org.example.magazine.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * - **Order**: заказ
 * - `id`: уникальный идентификатор заказа
 * - `user`: пользователь, который сделал заказ
 * - `products`: список товаров в заказе
 * - `totalPrice`: общая стоимость заказа
 * - `status`: статус заказа (например, PENDING, COMPLETED, CANCELED)
 * - `orderDate`: дата заказа
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_product")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private AppUser appUser;
    @OneToMany
    private List<Product> products;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDate orderDate = LocalDate.now();
}
