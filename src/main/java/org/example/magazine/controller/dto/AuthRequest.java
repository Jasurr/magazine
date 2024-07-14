package org.example.magazine.controller.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
