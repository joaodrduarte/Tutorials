package tutorial.codejavayt.SpringJwtAuthentication.JWT;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tutorial.codejavayt.SpringJwtAuthentication.User.User;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; //24H
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    @Value("${app.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(User user){
        return Jwts.builder()
                .setSubject(user.getId() + "," + user.getEmail())
                .setIssuer("CodeJava_JD")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }

    public boolean validateAccessToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException ex){
            LOGGER.error("JWT Expired", ex);
        }catch(IllegalArgumentException ex){
            LOGGER.error("Token is null, empty or has only whitespaces", ex);
        }catch(MalformedJwtException ex){
            LOGGER.error("JWT is Invalid", ex);
        }catch(UnsupportedJwtException ex){
            LOGGER.error("JWT is not Supported", ex);
        }catch(SignatureException ex){
            LOGGER.error("Signature validation failed", ex);
        }
        return false;
    }

    public String getSubject(String token){
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

}
