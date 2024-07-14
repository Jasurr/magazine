package org.example.magazine.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UserUtil {

    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Assuming your principal is of type UserDetails or a custom User class
            return ((User) authentication.getPrincipal()).getUsername();
        }
        return null; // or handle unauthenticated case
    }
}
