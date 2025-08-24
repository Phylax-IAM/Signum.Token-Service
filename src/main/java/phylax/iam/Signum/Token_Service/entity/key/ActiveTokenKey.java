package phylax.iam.Signum.Token_Service.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a composite primary key for the ActiveToken entity.
 * <p>
 * This key uniquely identifies an active token for a given combination of:
 * <ul>
 *     <li>{@code userId} - The UUID of the user</li>
 *     <li>{@code tenantId} - The UUID of the tenant</li>
 *     <li>{@code deviceId} - The UUID of the device</li>
 * </ul>
 *
 * Implements {@link Serializable} to satisfy JPA requirements for composite keys.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class ActiveTokenKey implements Serializable {

    /**
     * Unique identifier of the user who owns the token.
     */
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    /**
     * Unique identifier of the tenant to which the user belongs.
     */
    @Column(name = "tenant_id", nullable = false, updatable = false)
    private UUID tenantId;

    /**
     * Unique identifier of the device associated with the token.
     */
    @Column(name = "device_id", nullable = false, updatable = false)
    private UUID deviceId;
}