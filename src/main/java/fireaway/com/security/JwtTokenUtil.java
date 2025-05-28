package fireaway.com.security;

import fireaway.com.domainmodel.enuns.PerfilUsuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final String SECRET_BASE64 = "n0DpN8vpYlZx4K5GlqsfTejKu3CwFmRtuWZ3rJ+wZTT3XY8mEK6S9Fg7b8vtlXcVzNQSTaVq6XcNc8jw7coA9g==";

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_BASE64);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, PerfilUsuario perfil) {
        return Jwts.builder()
                .setSubject(email)
                .claim("perfil", perfil.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
