package com.lin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {


    private static long expiration = 1800000;


    //密钥

    private static String secret = "232139eoriewpore";


    //生成token
    public static String getToken(String uuid){
        Map<String, Object> map = new HashMap<>();
        map.put("uuid",uuid);
        return generatorToken(map);
    }

    /**
     * 失效时间
     *
     * @return
     */
    private static Date generatorExpiration() {
        return new Date(System.currentTimeMillis() + expiration);
    }


    private static String generatorToken(Map<String,Object> map) {
        return Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    /**
     * 根据token获取uuid
     *
     * @return
     */
    public static String getUserNameFormToken(String token) {
        String uuid = null;
        try {
            Claims claims = getClaimsFromToken(token);
            uuid = claims.get("uuid").toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return uuid;
    }


    /**
     * 根据token获取负载
     *
     * @return
     */
    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    public static void main(String[] args) {
     //   System.out.println(getToken(UUID.randomUUID().toString()));
        System.out.println(getUserNameFormToken("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NzA5OTY0MzcsInV1aWQiOiJjYjYwNzdhMS1jYzJhLTQyZWItYWRiMC01ZjA3NTIyMzIwMWMifQ.X2TmEi51ZQ1Ma6__GQj0DWq1cgR-3Z5iEL48aExQr1WTuxN6HCWKmxdblogbk0pbZeKYzJ3Bs66HvbHq14csSQ"));
    }

}
