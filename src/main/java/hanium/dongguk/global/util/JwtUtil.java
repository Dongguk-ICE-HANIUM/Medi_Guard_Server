package hanium.dongguk.global.util;

import hanium.dongguk.global.constants.Constants;
import hanium.dongguk.global.dto.JwtDto;
import hanium.dongguk.user.core.domain.ERole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil implements InitializingBean {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token.expiration}")
    @Getter
    private Integer accessExpiration;

    @Value("${jwt.refresh-token.expiration}")
    @Getter
    private Integer refreshExpiration;

    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UUID id, ERole role, Integer expiration) {
        Claims claims = Jwts.claims();
        claims.put(Constants.CLAIM_USER_ID, id.toString());
        if (role != null)
            claims.put(Constants.CLAIM_USER_ROLE, role);

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public JwtDto generateTokens(UUID id, ERole role) {
        return JwtDto.of(
                generateToken(id, role, accessExpiration),
                generateToken(id, role, refreshExpiration)
        );
    }
}