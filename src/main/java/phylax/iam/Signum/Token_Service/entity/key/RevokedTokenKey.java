package phylax.iam.Signum.Token_Service.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Composite key representation for a revoked token record.
 * <p>
 * This embeddable class is used as a composite identifier in the revoked token entity.
 * It uniquely identifies a revoked token by combining:
 * <ul>
 *   <li>{@code userId} – ID of the user associated with the token</li>
 *   <li>{@code tenantId} – ID of the tenant under which the token was issued</li>
 *   <li>{@code deviceId} – ID of the device where the token was used</li>
 *   <li>{@code tokenId} – Unique identifier of the token itself</li>
 * </ul>
 * The {@code revokedAt} timestamp is automatically initialized at persistence
 * if not explicitly provided.
 * </p>
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
     * Unique identifier of the user to whom the token belonged.
     */
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    /**
     * Unique identifier of the tenant under which the token was issued.
     */
    @Column(name = "tenant_id", nullable = false, updatable = false)
    private UUID tenantId;

    /**
     * Unique identifier of the device where the token was used.
     */
    @Column(name = "device_id", nullable = false, updatable = false)
    private UUID deviceId;

    /**
     * Unique identifier of the token being revoked.
     */
    @Column(name = "token_id", nullable = false, updatable = false)
    private UUID tokenId;
}