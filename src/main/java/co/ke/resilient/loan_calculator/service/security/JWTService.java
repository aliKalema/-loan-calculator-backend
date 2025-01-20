package co.ke.resilient.loan_calculator.service.security;

import co.ke.resilient.loan_calculator.model.security.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JWTService {
    private final String key = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

    public String generateJwt(User user) throws ParseException {
        Date date= new Date();
        return  Jwts.builder()
                .setIssuer("MFA Server")
                .setSubject("JWT Auth Token")
                .setClaims(generateClaims(user))
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + (long) 86400000))
                .signWith(secretKey)
                .compact();
    }

    public Authentication validateJwt(String jwt) {
        JwtParser jwtParser = getJwtParser();
        Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
        String username = (String)claims.getOrDefault("username",null);
        List<String> roles = claims.get("roles", List.class);
        List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        if(Objects.nonNull(username)){
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
        return null;
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
    }

    public Map< String,Object> generateClaims(User user){
        return Map.of(
             "username", user.getUsername(),
             "email", user.getEmail(),
             "roles", user.getRoleGroup().getRoles()
        );
    }

    public Object getClaim(String jwtToken,String claimKey){
        JwtParser jwtParser = getJwtParser();
        Claims claims = jwtParser.parseClaimsJws(jwtToken).getBody();
        return claims.getOrDefault(claimKey,null);
    }
}
