package com.lokapos.services.impl;

import com.lokapos.constants.AuthConstant;
import com.lokapos.entities.Account;
import com.lokapos.exception.NotFoundException;
import com.lokapos.repositories.AccountRepository;
import com.lokapos.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.lokapos.constants.AuthConstant.HEADER_X_EMAIL;
import static com.lokapos.constants.AuthConstant.HEADER_X_WHO;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = AuthConstant.SECRET;
    private static final Long expiration = AuthConstant.EXPIRATION_TIME;

    @Autowired
    private AccountRepository accountRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(UserDetails userDetails) {

        Optional<Account> account = accountRepository.findByEmailAndActiveIsTrue(userDetails.getUsername());
        if (account.isEmpty()) {
            throw new NotFoundException("Account Not Found");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(HEADER_X_WHO, account.get().getId());
        claims.put(HEADER_X_EMAIL, account.get().getEmail());
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername()).setExpiration(new Date(System.currentTimeMillis() + expiration)).signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }
}
