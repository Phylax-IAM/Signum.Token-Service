package phylax.iam.Signum.Token_Service.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a composite primary key for the {@code RevokedToken} entity.
 *
 * <p>
 * This key uniquely identifies a revoked token in the system by combining:
 * </p>
 * <ul>
 *   <li>{@code subject} – The unique identifier (UUIDv7) of the entity
 *   (e.g., user, tenant, or device) the token was originally issued for.</li>
 *   <li>{@code tokenId} – The unique identifier (UUIDv7) of the token itself.</li>
 * </ul>
 *
 * <h2>JPA Details</h2>
 * <ul>
 *   <li>Annotated with {@link Embeddable} so it can be used as a
 *   composite key in the {@code RevokedToken} entity.</li>
 *   <li>Implements {@link Serializable}, as required by JPA for
 *   composite identifiers.</li>
 *   <li>Uses {@link EqualsAndHashCode} to ensure consistent behavior
 *   in equality checks and hash-based collections.</li>
 * </ul>
 *
 * <h2>Lombok Annotations</h2>
 * <ul>
 *   <li>{@link Getter}, {@link Setter} – Auto-generates accessor methods.</li>
 *   <li>{@link Builder} – Provides a fluent API for constructing instances.</li>
 *   <li>{@link NoArgsConstructor}, {@link AllArgsConstructor} – Generate
 *   default and all-arguments constructors.</li>
 *   <li>{@link EqualsAndHashCode} – Ensures identity consistency.</li>
 * </ul>
 *
 * <p>
 * Note: The {@code revokedAt} timestamp is expected to be handled at the
 * entity level (not in this key) and is typically initialized automatically
 * upon persistence if not explicitly provided.
 * </p>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class RevokedTokenKey implements Serializable {

    /**
     * The unique identifier (UUIDv7) of the subject associated with the revoked token.
     * <p>
     * This subject may represent a user, tenant, device, or any entity
     * that the revoked token was originally issued for.
     * </p>
     */
    @Column(name = "subject", nullable = false, updatable = false)
    private UUID subject;

    /**
     * Unique identifier of the token being revoked.
     */
    @Column(name = "token_id", nullable = false, updatable = false)
    private UUID tokenId;
}