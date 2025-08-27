package phylax.iam.Signum.Token_Service.entity;

import jakarta.persistence.*;
import lombok.*;
import phylax.iam.Signum.Token_Service.common.constant.TokenClassConstant;
import phylax.iam.Signum.Token_Service.common.constant.TokenTypeConstant;
import phylax.iam.Signum.Token_Service.entity.key.RevokedTokenKey;

import java.time.Instant;


/**
 * Entity representing a revoked user token record in the system.
 * <p>
 * This entity is used to track tokens that have been explicitly revoked
 * before their natural expiration, ensuring that such tokens are not
 * considered valid during authentication or authorization checks.
 * </p>
 *
 * <h2>Persistence Details</h2>
 * <ul>
 *   <li>Maps to the database table {@code user_revoked_token}.</li>
 *   <li>Uses {@link RevokedTokenKey} as a composite primary key to uniquely
 *       identify revoked tokens across users, tenants, devices, and token IDs.</li>
 *   <li>Each record persists both the token details and associated metadata
 *       (revoked/expiry timestamps).</li>
 * </ul>
 *
 * <h2>Lombok Annotations</h2>
 * <ul>
 *   <li>{@link Getter}, {@link Setter} – auto-generates accessors.</li>
 *   <li>{@link Builder} – enables fluent builder pattern for constructing instances.</li>
 *   <li>{@link NoArgsConstructor}, {@link AllArgsConstructor} – provides default
 *       and all-args constructors.</li>
 * </ul>
 *
 * <h2>Lifecycle Management</h2>
 * <ul>
 *   <li>{@code revokedAt} is automatically initialized to the current timestamp
 *       ({@link Instant#now()}) if not explicitly provided.</li>
 *   <li>{@code expiresAt} must be explicitly provided before persistence to ensure
 *       correct cleanup of expired revoked-token records.</li>
 * </ul>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
@Entity
@Table(name = "user_revoked_token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRevokedTokenEntity {

    /**
     * Composite key representing the unique identity of the revoked token.
     * <p>
     * Includes identifiers for the user, tenant, device, and token.
     * </p>
     */
    @EmbeddedId
    private RevokedTokenKey revokedTokenKey;

    /**
     * The token string value that was revoked.
     */
    @Column(name = "token", nullable = false, updatable = false)
    private String token;

    /**
     * Type of token that was revoked (e.g., JWT, SHA).
     */
    @Column(name = "token_type", nullable = false, updatable = false)
    private TokenTypeConstant tokenTypeConstant;

    /**
     * Class/category of the revoked token
     * (e.g., ACCESS, REFRESH, or other custom classification).
     */
    @Column(name = "token_class", nullable = false, updatable = false)
    private TokenClassConstant tokenClassConstant;

    /**
     * Timestamp when the token was revoked.
     * <p>
     * Automatically initialized to {@link Instant#now()} during persistence
     * if not explicitly set.
     * </p>
     */
    @Column(name = "revoked_at", nullable = false, updatable = false)
    private Instant revokedAt;

    /**
     * Timestamp when the revoked token record will expire.
     * <p>
     * Must be explicitly set before persistence to ensure proper
     * cleanup and record lifecycle management.
     * </p>
     */
    @Column(name = "expires_at", nullable = false, updatable = false)
    private Instant expiresAt;

    /**
     * Lifecycle hook triggered before entity persistence.
     * <p>
     * Ensures that {@link #revokedAt} is assigned a default value
     * (current timestamp) if not explicitly provided.
     * </p>
     */
    @PrePersist
    public void onCreate() {

        if(this.revokedAt == null) {
            this.revokedAt = Instant.now();
        }
    }
}
