package phylax.iam.Signum.Token_Service.dto.payload;

import lombok.*;
import java.util.UUID;

import phylax.iam.Signum.Token_Service.common.constant.TokenTypeConstant;
import phylax.iam.Signum.Token_Service.common.constant.TokenClassConstant;

/**
 * Data Transfer Object representing the payload of a token.
 * <p>
 * This class encapsulates all essential information associated with a token,
 * including its unique identifier, the subject it belongs to, its type, and its class.
 * </p>
 * <p>
 * Lombok annotations are used to automatically generate getters, setters, constructors,
 * builder methods, and equals/hashCode implementations.
 * </p>
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public final class AuthTokenPayloadDTO {

    /** Unique identifier for the subject (e.g., user or entity) associated with the token. */
    private UUID subject;

    /** The type of the token, represented by {@link TokenTypeConstant}. */
    private TokenTypeConstant tokenTypeConstant;

    /** The class/category of the token, represented by {@link TokenClassConstant}. */
    private TokenClassConstant tokenClassConstant;
}
