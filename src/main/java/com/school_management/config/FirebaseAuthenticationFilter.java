package com.school_management.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.school_management.services.impl.BulletinService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            logger.warn("Authorization header not found");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();

            List<GrantedAuthority> authorities = List.of();

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(uid, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (FirebaseAuthException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Firebase Token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

