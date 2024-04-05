package com.shop.alcoshopspring.security;

import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Value;

import java.security.SecureRandom;

public class SecretHolder {
    @Value("${security.jwt.secret:secret}")
    private static final String jwtSecret = new SecureRandom().toString();

    public static String getEncodedSecret() {
        return TextCodec.BASE64.encode(jwtSecret);
    }
}
