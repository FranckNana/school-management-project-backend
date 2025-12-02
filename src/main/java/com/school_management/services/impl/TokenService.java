package com.school_management.services.impl;

import com.school_management.services.ITokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class TokenService implements ITokenService {

    @Value("${token.secret}")
    private String SECRET;

    private static final long EXPIRATION_MS =new Date(System.currentTimeMillis() + 5 * 60 * 1000).getTime();  // 5 minutes

    @Override
    public String generateToken(Long bulletinId) {
        long now = System.currentTimeMillis();
        long exp = now + EXPIRATION_MS;

        String raw = bulletinId + ":" + exp + ":" + SECRET;
        String signature = Base64.getEncoder().encodeToString(raw.getBytes());

        return bulletinId + "." + exp + "." + signature;
    }

    @Override
    public boolean isValid(Long bulletinId, String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;

            Long id = Long.parseLong(parts[0]);
            long exp = Long.parseLong(parts[1]);
            String signature = parts[2];

            if (!id.equals(bulletinId)) return false;
            if (System.currentTimeMillis() > exp) return false;

            String raw = id + ":" + exp + ":" + SECRET;
            String expectedSignature =
                    Base64.getEncoder().encodeToString(raw.getBytes());

            return expectedSignature.equals(signature);

        } catch (Exception e) {
            return false;
        }
    }
}
