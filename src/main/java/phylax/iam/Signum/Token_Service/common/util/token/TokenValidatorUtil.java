package phylax.iam.Signum.Token_Service.common.util.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phylax.iam.Signum.Token_Service.common.exception.InvalidJWTException;

import javax.crypto.SecretKey;

public final class TokenValidatorUtil {

    private final static Logger logger = LoggerFactory.getLogger(TokenValidatorUtil.class);

    private static Claims extractClaims(String token, SecretKey secretKey) {

        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            logger.debug("Invalid JWT Provided", e);
            throw new InvalidJWTException("Invalid JWT Provided");
        }
    }

    public static String extractEncryptedPayload(String token, String payloadKeyName, SecretKey secretKey) {
        return extractClaims(token, secretKey)
                .get(
                        payloadKeyName,
                        String.class
                );
    }
}
