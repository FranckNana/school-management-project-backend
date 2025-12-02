package com.school_management.services;

public interface ITokenService {
    public String generateToken(Long bulletinId);
    public boolean isValid(Long bulletinId, String token);
}
