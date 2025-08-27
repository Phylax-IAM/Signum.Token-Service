package phylax.iam.Signum.Token_Service.dto.payload;

import lombok.*;
import java.util.UUID;

import phylax.iam.Signum.Token_Service.common.constant.TokenClassConstant;
import phylax.iam.Signum.Token_Service.common.constant.TokenTypeConstant;


/**
 * Data Transfer Object (DTO) representing the payload of a refresh token.
 * <p>
 * This DTO encapsulates all essential metadata required to validate and
 * process a refresh token, including identifiers and classification details.
 * </p>
 *
 * <p>
 * The class is immutable in structure but mutable in fields (due to Lombok
 * {@code @Getter} / {@code @Setter}), and it is primarily used for data
 * transportation between different layers of the application.
 * </p>
 *
 * <p>
 * Typical usage includes:
 * <ul>
 *   <li>Storing token-related information in persistence or cache layers</li>
 *   <li>Passing token metadata across services for validation</li>
 * </ul>
 * </p>
 *
 * <p>
 * An instance can be conveniently created using the Lombok-generated
 * {@code @Builder} pattern, default constructor, or all-arguments constructor.
 * </p>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public final class RefreshTokenPayloadDTO {

    /**
     * Unique identifier for the authentication token associated with this refresh token.
     * <p>
     * This ID typically refers to the parent access token that is being refreshed.
     * </p>
     */
    private UUID authTokenId;

    /**
     * Unique identifier for the subject (e.g., user, service, or entity) associated with the token.
     * <p>
     * This ID binds the refresh token to its rightful owner.
     * </p>
     */
    private UUID subject;

    /**
     * The type of the token.
     * <p>
     * Represented by {@link TokenTypeConstant}, this defines the specific
     * token usage type (e.g., ACCESS, REFRESH).
     * </p>
     */
    private TokenTypeConstant tokenTypeConstant;

    /**
     * The classification of the token.
     * <p>
     * Represented by {@link TokenClassConstant}, this defines the broader
     * category of the token (e.g., BEARER, MAC).
     * </p>
     */
    private TokenClassConstant tokenClassConstant;
}
