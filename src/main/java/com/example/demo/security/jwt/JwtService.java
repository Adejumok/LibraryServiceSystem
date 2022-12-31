package com.example.demo.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtService implements JwtTokenProvider {
    public final String AUTHORITIES_KEY = System.getenv("AUTHORITIES_KEY");
    private final static Long TOKEN_VALIDITY_PERIOD = (long) (24 * 10 * 3600);


    @Override
    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Header<?> getHeader(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .build()
                .parseClaimsJws(token)
                .getHeader();
    }

    @Override
    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    @Override
    public String generateToken(Authentication authentication) {
        log.info("Authentication in generate token --> {}",
                authentication);
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        log.info("Authorities --> {}", authorities);
        String jwts = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +
                        TOKEN_VALIDITY_PERIOD))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
        log.info("Jwts -->{}", jwts);
        return jwts;
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String generateTokenForVerification(String id) {
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_PERIOD))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthenticationToken(String token, Authentication existingAuth, UserDetails userDetails) {
        final JwtParserBuilder jwtParser = Jwts.parserBuilder()
                .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256));
        final Jws<Claims> claimsJws = jwtParser.build().parseClaimsJws(token);
        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY)
                        .toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
        log.info("Authorities here --> {}", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails,"",authorities);
    }
}
