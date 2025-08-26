package phylax.iam.Signum.Token_Service.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import phylax.iam.Signum.Token_Service.common.constant.TokenClass;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a composite primary key for the {@code ActiveToken} entity.
 *
 * <p>
 * This key uniquely identifies an active token in the system by combining:
 * </p>
 * <ul>
 *   <li>{@code subject} – The unique identifier (UUIDv7) of the entity
 *   (e.g., user, tenant, or device) the token is issued for.</li>
 *   <li>{@code tokenClass} – The classification of the token that
 *   distinguishes its purpose, such as authentication, refresh,
 *   or temporary usage.</li>
 * </ul>
 *
 * <h2>JPA Details</h2>
 * <ul>
 *   <li>Annotated with {@link Embeddable} to mark it as a composite
 *   primary key that can be embedded in the {@code ActiveToken} entity.</li>
 *   <li>Implements {@link Serializable}, as required by JPA for composite keys.</li>
 *   <li>Uses {@link EqualsAndHashCode} to ensure correct equality and
 *   hashing behavior for entity identity management.</li>
 * </ul>
 *
 * <h2>Lombok Annotations</h2>
 * <ul>
 *   <li>{@link Getter}, {@link Setter} – Auto-generates accessor methods.</li>
 *   <li>{@link Builder} – Provides a fluent API for constructing instances.</li>
 *   <li>{@link NoArgsConstructor}, {@link AllArgsConstructor} – Generate default and
 *   full-argument constructors.</li>
 *   <li>{@link EqualsAndHashCode} – Ensures consistent equality checks
 *   and proper use in hash-based collections.</li>
 * </ul>
 *
 * @see TokenClass
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
public class ActiveTokenKey implements Serializable {

    /**
     * The unique identifier (UUIDv7) of the subject associated with this token.
     * <p>
     * The subject may represent a user, tenant, device, or any entity
     * that owns or is associated with the token.
     * </p>
     */
    @Column(name = "subject", nullable = false, updatable = false)
    private UUID subject;

    /**
     * Class of the token that distinguishes its purpose in
     * authentication and authorization flows.
     *
     * <p>
     * Possible values are defined in {@link TokenClass}, such as
     * {@code AUTHENTICATION}, {@code REFRESH}, or {@code TEMPORARY}.
     * </p>
     */
    @Column(name = "token_class", nullable = false, updatable = false)
    private TokenClass tokenClass;
}