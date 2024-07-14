package org.example.magazine.controller;

import org.example.magazine.controller.dto.AuthRequest;
import org.example.magazine.model.AppUser;
import org.example.magazine.model.UserRoles;
import org.example.magazine.security.JwtUtil;
import org.example.magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        Set<String> roles = userService.findByUsername(userDetails.getUsername()).get().getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        return jwtUtil.generateToken(
                userDetails.getUsername(),
                roles
        );
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AppUser user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Set<UserRoles> roles = new HashSet<>();
            roles.add(UserRoles.GUEST);
            user.setRoles(roles);
            userService.save(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/register-admin")
    public String registerAdmin(@RequestBody AppUser user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<UserRoles> roles = new HashSet<>();
        roles.add(UserRoles.ADMIN);
        roles.add(UserRoles.USER);
        user.setRoles(roles);
        userService.save(user);
        return "Admin registered successfully";
    }
}
