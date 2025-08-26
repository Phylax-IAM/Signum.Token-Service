package phylax.iam.Signum.Token_Service.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import phylax.iam.Signum.Token_Service.entity.key.RevokedTokenKey;
import phylax.iam.Signum.Token_Service.entity.UserRevokedTokenEntity;

/**
 * Repository interface for managing {@link UserRevokedTokenEntity} persistence operations.
 *
 * <p>This repository extends {@link JpaRepository}, providing standard CRUD
 * functionality for entities identified by a composite primary key
 * {@link RevokedTokenKey}.</p>
 *
 * <p>Custom query methods include:</p>
 * <ul>
 *   <li>{@link #findByRevokedTokenKey(RevokedTokenKey)} – Retrieves a revoked token entity
 *       by its composite key.</li>
 *   <li>{@link #deleteExpiredToken(Instant)} – Deletes all revoked tokens that have
 *       expired before a given timestamp.</li>
 * </ul>
 *
 * @see UserRevokedTokenEntity
 * @see RevokedTokenKey
 */
@Repository
public interface UserRevokedTokenRepository extends JpaRepository<UserRevokedTokenEntity, RevokedTokenKey> {

    // Read

    /**
     * Retrieves a {@link UserRevokedTokenEntity} by its composite key.
     *
     * @param revokedTokenKey the composite key consisting of subject and token ID
     * @return an {@link Optional} containing the revoked token if found, or empty otherwise
     */
    Optional<UserRevokedTokenEntity> findByRevokedTokenKey(RevokedTokenKey revokedTokenKey);

    // Delete

    /**
     * Deletes all revoked tokens that have expired before the given timestamp.
     *
     * @param thisInstant the cutoff {@link Instant}; tokens with an {@code expiresAt}
     *                    earlier than this value will be deleted
     */
    @Modifying
    @Query("DELETE FROM UserRevokedTokenEntity rt WHERE rt.expiresAt < :now")
    void deleteExpiredToken(@Param("now") Instant thisInstant);
}
