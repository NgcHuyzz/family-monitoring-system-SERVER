package com.family.server.security;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtil {

    // Nhớ đổi thành secret dài, khó đoán, lưu ở .env càng tốt
    private static final String SECRET_KEY = "CHANGE_THIS_TO_A_LONG_SECRET";

    // 7 ngày
    private static final long EXPIRATION_MS = 7L * 24 * 60 * 60 * 1000;

    public static String generateToken(String userId, String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(userId)                    // subject = userId
                .claim("username", username)           // thêm claim username
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUserId(String token) {
        return parseToken(token).getSubject();
    }

    public static String getUsername(String token) {
        Object username = parseToken(token).get("username");
        return username != null ? username.toString() : null;
    }
}
