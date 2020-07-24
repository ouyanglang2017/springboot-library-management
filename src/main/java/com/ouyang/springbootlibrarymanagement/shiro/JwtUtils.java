package com.ouyang.springbootlibrarymanagement.shiro;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtils {

    public static void main(String[] args) {
        getToken("admin");
    }

    //私钥
    final static String base64EncodedSecretKey = "cXdoZGlhc2RpanBvcWpkd3FvamRxcXdxZAog";
    //过期时间,一天
    final static long TOKEN_EXP = 1000 * 60 * 60 * 24;

    //生成一个token
    public static String getToken(String username) {
        String compact = Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP)).signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey).compact();
        return compact;
    }

    //校验token
    public static boolean checkToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser().setSigningKey(base64EncodedSecretKey);
            Claims body = jwtParser.parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            //过期
            return false;
        } catch (Exception e2) {
            //用户未登录
            return false;
        }

    }
    //根据token拿username
    public static String getUserNameByToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser().setSigningKey(base64EncodedSecretKey);
            Claims body = jwtParser.parseClaimsJws(token).getBody();
            String subject = body.getSubject();
            return subject;
        } catch (ExpiredJwtException e) {
            //过期
            return null;
        } catch (Exception e2) {
            //用户未登录
            return null;
        }

    }

}
