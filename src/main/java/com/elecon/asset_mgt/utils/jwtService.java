package com.elecon.asset_mgt.utils;

import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class jwtService{
    //    @Value("${token.signing.key}")
    private String jwtSigningKey = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
    public String extractEmployee_name(String token) { //extractUserName
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(EmployeeModel user) {
//        System.out.println("generate token public");
        return generateToken(new HashMap<>(), user);
    }
    public boolean isTokenValid(String token) {
//        final String employeeName = extractEmployee_name(token);
//        System.out.println("in isvalidtoken function public");
//        System.out.println("isTokenExpired(token)"+isTokenExpired(token));
        return (isTokenExpired(token));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
//        System.out.println("extractclaim private"+extractAllClaims(token));
        final Claims claims = extractAllClaims(token);
//        System.out.println("claims"+claims);
//        System.out.println("claimresolver.apply() "+claimsResolvers.apply(claims));
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, EmployeeModel user) {
//        System.out.println("generate token private");
//        System.out.println("Date(System.currentTimeMillis()) "+ new Date(System.currentTimeMillis()));
//        System.out.println("new Date(System.currentTimeMillis() + 60000) "+new Date(System.currentTimeMillis() + 5000));
        extraClaims.put("user_role", user.getRole());
        extraClaims.put("employeeCode", user.getEmployeeCode());
        return Jwts.builder().setClaims(extraClaims).setSubject(user.getEmployee_name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token) {
//        System.out.println("in isvalidtoken function private"+extractExpiration(token).before(new Date()));
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
//        System.out.println("extractExpiration private"+extractClaim(token, Claims::getExpiration));
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
//        System.out.println("extarctallclaims");
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

