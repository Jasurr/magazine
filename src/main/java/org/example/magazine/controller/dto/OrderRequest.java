package org.example.magazine.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.magazine.model.Product;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private List<Product> products;
}
