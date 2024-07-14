package org.example.magazine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a product with various attributes such as name, description, price, and stock.
 *
 * - **Product**: товар
 *   - `id`: уникальный идентификатор товара (Unique identifier for the product)
 *   - `name`: название товара (Name of the product)
 *   - `description`: описание товара (Description of the product)
 *   - `price`: цена товара (Price of the product)
 *   - `stock`: количество товара на складе (Stock quantity of the product)
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product {
    /**
     * Unique identifier for the product.
     *
     * Уникальный идентификатор товара
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * Name of the product.
     *
     * Название товара
     */
    private String name;
    /**
     * Description of the product.
     *
     * Описание товара
     */
    private String description;
    /**
     * Price of the product.
     *
     * Цена товара
     */
    private Double price;
    /**
     * Stock quantity of the product.
     *
     * Количество товара на складе
     */
    private Integer stock;
}
