package org.example.magazine.service;

import org.example.magazine.model.AppUser;
import org.example.magazine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.NameAlreadyBoundException;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public AppUser save(AppUser user) throws Exception {
        Optional<AppUser> appUser = userRepository.findByUsername(user.getUsername());
        if (appUser.isPresent()) {
            throw new Exception("Username already exists.");
        }
        return userRepository.save(user);
    }

    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            Set<SimpleGrantedAuthority> authorities = user.get().getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toSet());
            return User
                    .withUsername(user.get().getUsername())
                    .password(user.get().getPassword())
                    .authorities(authorities)
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
