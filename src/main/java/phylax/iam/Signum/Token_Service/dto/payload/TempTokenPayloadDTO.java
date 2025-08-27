package phylax.iam.Signum.Token_Service.dto.payload;

import lombok.*;
import java.util.UUID;

import phylax.iam.Signum.Token_Service.common.constant.TokenClassConstant;
import phylax.iam.Signum.Token_Service.common.constant.TokenTypeConstant;


/**
 * Data Transfer Object (DTO) representing the payload of a temporary token.
 * <p>
 * A temporary token is typically used for short-lived authentication or
 * validation scenarios, such as one-time codes (OTPs), password resets,
 * or email/phone verification flows.
 * </p>
 *
 * <p>
 * This DTO encapsulates essential metadata required for managing
 * temporary tokens, including the subject identifier, one-time code,
 * and classification details.
 * </p>
 *
 * <p>
 * Instances of this DTO can be created using the Lombok-generated
 * {@code @Builder}, default constructor, or all-arguments constructor.
 * </p>
 *
 * @author
 *   Pragyanshu Rai
 * @since 1.0
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public final class TempTokenPayloadDTO {

    /**
     * Unique identifier for the subject (e.g., user, service, or entity)
     * associated with the temporary token.
     */
    private UUID subject;

    /**
     * A one-time code (OTP) or verification string associated with the token.
     * <p>
     * Typically used for actions such as multi-factor authentication,
     * password resets, or account verification.
     * </p>
     */
    private String oneTimeCode;

    /**
     * The type of the token.
     * <p>
     * Represented by {@link TokenTypeConstant}, this defines the
     * specific token usage type (e.g., TEMPORARY, REFRESH).
     * </p>
     */
    private TokenTypeConstant tokenTypeConstant;

    /**
     * The classification of the token.
     * <p>
     * Represented by {@link TokenClassConstant}, this defines the
     * broader category of the token (e.g., BEARER, MAC).
     * </p>
     */
    private TokenClassConstant tokenClassConstant;
}
