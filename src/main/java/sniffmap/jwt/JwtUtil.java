//package sniffmap.jwt;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//import java.security.Key;
//import java.util.Date;
//
//public class JwtUtil {
//    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    private static final long EXPIRATION_TIME = 864_000_000; // 10일
//
//    public static String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(key)
//                .compact();
//    }
//
//    public static String getUsernameFromToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//}


package sniffmap.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
public static final String AUTHORIZATION_HEADER = "Authorization";

// 사용자 권한 값의 KEY
public static final String AUTHORIZATION_KEY = "auth";

// Token 식별자   Bearer 란 JWT 혹은 OAuth에 대한 토큰을 사용한다는 표시
public static final String BEARER_PREFIX = "Bearer ";

// 토큰 만료시간
public final long TOKEN_TIME = 60 * 60 * 1000L;

// application.properties 에 있는 secretKey 값을 가져오는 방법
@Value("${jwt.secret.key}")
private String secretKey;

private Key key;

// 암호화 알고리즘 HS256으로 사용할 것임
private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

//// 로그 찍는 것
//public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

// @PostConstruct는 딱 한 번만 받아오면 되는 값을 사용 할 때마다 요청을 새로 호출하는 실수를 방지하기 위해 사용
@PostConstruct
public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes); // key값에 우리가 사용할 secret 값이 담겨진다.
}
}
