package phylax.iam.Signum.Token_Service.entity;

import java.util.UUID;
import java.time.Instant;
import java.time.Duration;

import lombok.*;

import jakarta.persistence.*;

import phylax.iam.Signum.Token_Service.entity.key.ActiveTokenKey;
import phylax.iam.Signum.Token_Service.common.constant.TokenTypeConstant;

/**
 * Entity representing an active token issued to a user.
 *
 * <p>
 * This entity persists tokens that are currently valid and can be used for
 * authentication or authorization until they expire or are explicitly revoked.
 * Each record uniquely maps to a combination of user, tenant, device, and token class.
 * </p>
 *
 * <h2>Persistence Details</h2>
 * <ul>
 *   <li>Maps to the database table {@code user-active-token}.</li>
 *   <li>Composite primary key defined by {@link ActiveTokenKey}
 *       (user, tenant, device identifiers, and token class).</li>
 *   <li>Each token is assigned a unique {@code tokenId} generated using
 *       a time-based UUID strategy via {@link org.hibernate.annotations.UuidGenerator}.</li>
 * </ul>
 *
 * <h2>Lombok Annotations</h2>
 * <ul>
 *   <li>{@link Getter}, {@link Setter} – auto-generates accessors.</li>
 *   <li>{@link Builder} – enables fluent builder-style object construction.</li>
 *   <li>{@link NoArgsConstructor}, {@link AllArgsConstructor} – provides
 *       default and all-args constructors.</li>
 * </ul>
 *
 * <h2>Lifecycle Management</h2>
 * <ul>
 *   <li>{@code issuedAt} is initialized at persist time if not set.</li>
 *   <li>{@code expiresAt} defaults to 59 seconds after {@code issuedAt} if not set.</li>
 * </ul>
 *
 * @author
 *   Pragyanshu Rai
 * @since 1.0
 */
@Entity
@Table(name = "user-active-token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActiveTokenEntity {

    /**
     * Composite primary key consisting of user, tenant, and device identifiers.
     */
    @EmbeddedId
    private ActiveTokenKey activeTokenKey;

    /**
     * Unique identifier for the token.
     */
    @Column(name = "token_id", nullable = false, updatable = false)
    private UUID tokenId;

    /**
     * Encoded token value (e.g., JWT string, SHA hash).
     */
    @Column(name = "token_type", nullable = false, updatable = false)
    private String token;

    /**
     * Cryptographic type of token (e.g., {@link TokenTypeConstant#JWT}, {@link TokenTypeConstant#SHA}).
     */
    @Column(name = "token_type", nullable = false, updatable = false)
    private TokenTypeConstant tokenTypeConstant;

    /**
     * Timestamp at which the token was issued.
     * <p>
     * Automatically initialized during persistence if not explicitly set.
     * </p>
     */
    @Column(name = "issued_at", nullable = false, updatable = false)
    private Instant issuedAt;

    /**
     * Timestamp at which the token will expire.
     * <p>
     * Automatically initialized during persistence if not explicitly set.
     * Defaults to 59 seconds after {@link #issuedAt}.
     * </p>
     */
    @Column(name = "expires_at", nullable = false, updatable = false)
    private Instant expiresAt;

    /**
     * Lifecycle hook triggered before entity persistence.
     * <p>
     * Ensures that {@link #issuedAt} and {@link #expiresAt} are assigned sensible defaults
     * when not explicitly provided:
     * <ul>
     *   <li>{@code issuedAt} → current timestamp ({@link Instant#now()})</li>
     *   <li>{@code expiresAt} → 59 seconds after {@code issuedAt}</li>
     * </ul>
     * </p>
     */
    @PrePersist
    public void onCreate() {

        // set to default issue time if null
        if(this.issuedAt == null) {
            this.issuedAt = Instant.now();
        }

        // set to default expire time if null
        if(this.expiresAt == null) {
            this.expiresAt = issuedAt.plus(Duration.ofSeconds(59));
        }
    }
}
