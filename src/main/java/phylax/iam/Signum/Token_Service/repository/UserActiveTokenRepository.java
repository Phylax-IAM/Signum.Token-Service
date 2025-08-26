package phylax.iam.Signum.Token_Service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import phylax.iam.Signum.Token_Service.entity.key.ActiveTokenKey;
import phylax.iam.Signum.Token_Service.entity.UserActiveTokenEntity;

/**
 * Repository interface for managing persistence operations of
 * {@link UserActiveTokenEntity}.
 *
 * <p>
 * This repository extends {@link JpaRepository}, providing CRUD operations
 * for entities identified by the composite key {@link ActiveTokenKey}.
 * </p>
 *
 * <p>
 * In addition to standard JPA methods, this repository defines custom
 * query methods to support token lifecycle management.
 * </p>
 *
 * <h3>Custom Methods</h3>
 * <ul>
 *   <li>{@link #findByActiveTokenKey(ActiveTokenKey)} –
 *       Retrieves an active token entity by its composite key.</li>
 *   <li>{@link #deleteTokenByActiveKey(ActiveTokenKey)} –
 *       Deletes an active token entity by its composite key.</li>
 * </ul>
 *
 * @see UserActiveTokenEntity
 * @see ActiveTokenKey
 */
@Repository
public interface UserActiveTokenRepository extends JpaRepository<UserActiveTokenEntity, ActiveTokenKey> {

    // Read

    /**
     * Retrieves an {@link UserActiveTokenEntity} by its composite key.
     *
     * @param activeTokenKey the composite key consisting of subject and token class
     * @return an {@link Optional} containing the active token if found, or empty otherwise
     */
    Optional<UserActiveTokenEntity> findByActiveTokenKey(ActiveTokenKey activeTokenKey);

    // Update

    // Delete

    /**
     * Deletes an {@link UserActiveTokenEntity} by its composite key.
     *
     * <p>
     * This operation removes the record from persistence if a match is found.
     * It must be executed within a transactional context.
     * </p>
     *
     * @param activeTokenKey the composite key identifying the token to delete
     */
    @Modifying
    @Query("DELETE FROM UserActiveTokenEntity at WHERE at.activeTokenKey = :targetKey")
    void deleteTokenByActiveKey(@Param("targetKey") ActiveTokenKey activeTokenKey);
}
