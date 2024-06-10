package sniffmap.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import sniffmap.web.CustomUserDetailsService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@NoArgsConstructor
public class JwtTokenProvider {
    private final long VALID_MILISECOND = 1000L * 60 * 60; // 1 시간
//    private final CustomUserDetailsService userService;

//    public JwtTokenProvider(CustomUserDetailsService userService) {
//        this.userService = userService;
//    }

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key getSecretKey(String secretKey) {
        byte[] KeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(KeyBytes);
    }

    public String getUsername(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(getSecretKey(secretKey))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String jwtToken) {
        try {
            log.info("validate..");
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(getSecretKey(secretKey))
                    .build()
                    .parseClaimsJws(jwtToken);
            log.info("{}",claims.getBody().getExpiration());
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e) {
            return false;
        }
    }

//    public Authentication getAuthentication(String jwtToken) {
//        UserDetails userDetails = userService.loadUserByUsername(getEmail(jwtToken));
//        log.info("PASSWORD : {}",userDetails.getPassword());
//        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }


    public String generateToken(String username) {
        log.info("jkjkj");
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + VALID_MILISECOND))
                .signWith(getSecretKey(secretKey))
                .compact();
    }
}