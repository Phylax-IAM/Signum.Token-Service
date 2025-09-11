package phylax.iam.Signum.Token_Service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import phylax.iam.Signum.Token_Service.entity.key.ActiveTokenKey;
import phylax.iam.Signum.Token_Service.entity.ActiveTokenEntity;

/**
 * Repository interface for managing persistence operations of
 * {@link ActiveTokenEntity}.
 *
 * <p>
 * This repository extends {@link JpaRepository}, providing full CRUD
 * capabilities for active token entities identified by the composite key
 * {@link ActiveTokenKey}. It serves as the persistence abstraction for
 * storing, retrieving, and managing active tokens within the Signum
 * Token Service.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Provide standard JPA persistence operations (save, delete, find).</li>
 *   <li>Support token lifecycle operations through custom queries.</li>
 *   <li>Enable efficient lookups and deletions by subject or composite key.</li>
 * </ul>
 *
 * <h2>Custom Methods</h2>
 * <ul>
 *   <li>{@link #findByActiveTokenKey(ActiveTokenKey)} –
 *       Retrieve an active token entity by its composite key.</li>
 *   <li>{@link #findBySubject(UUID)} –
 *       Fetch all active tokens associated with a specific subject.</li>
 *   <li>{@link #countBySubject(UUID)} –
 *       Count the number of active tokens associated with a subject.</li>
 *   <li>{@link #deleteTokenByActiveKey(ActiveTokenKey)} –
 *       Delete a single active token identified by its composite key.</li>
 *   <li>{@link #deleteTokensBySubject(UUID)} –
 *       Bulk delete all active tokens belonging to a subject.</li>
 * </ul>
 *
 * <p><strong>Note:</strong> All modifying queries must be executed
 * within a transactional context. Ensure that service methods calling
 * {@code @Modifying} queries are annotated with
 * {@link org.springframework.transaction.annotation.Transactional}.
 * </p>
 *
 * @see ActiveTokenEntity
 * @see ActiveTokenKey
 */
@Repository
public interface ActiveTokenRepository extends JpaRepository<ActiveTokenEntity, ActiveTokenKey> {

    // -------------------------------------------------------------------------
    // Read
    // -------------------------------------------------------------------------

    /**
     * Retrieves a single {@link ActiveTokenEntity} by its composite key.
     *
     * @param activeTokenKey the composite key consisting of subject and token class
     * @return an {@link Optional} containing the active token if found, or empty otherwise
     */
    Optional<ActiveTokenEntity> findByActiveTokenKey(ActiveTokenKey activeTokenKey);

    @Query("SELECT at.tokenId FROM ActiveTokenEntity at WHERE at.activeTokenKey.subject = :targetSubject AND at.token = :targetToken")
    Optional<UUID> findIdBySubjectAndToken(@Param("targetSubject") UUID subject, @Param("targetToken") String token);

    /**
     * Retrieves all active tokens belonging to a given subject.
     *
     * @param subject the subject identifier (UUID) to match
     * @return a list of {@link ActiveTokenEntity} belonging to the subject
     */
    @Query("SELECT at FROM ActiveTokenEntity at WHERE at.activeTokenKey.subject = :targetSubject")
    List<ActiveTokenEntity> findBySubject(@Param("targetSubject") UUID subject);

    /**
     * Counts the number of active tokens for a given subject.
     *
     * @param subject the subject identifier (UUID) to match
     * @return the number of active tokens belonging to the subject
     */
    @Query("SELECT COUNT(*) FROM ActiveTokenEntity at WHERE at.activeTokenKey.subject = :targetSubject")
    long countBySubject(@Param("targetSubject") UUID subject);

    // -------------------------------------------------------------------------
    // Delete
    // -------------------------------------------------------------------------

    /**
     * Deletes a single {@link ActiveTokenEntity} by its composite key.
     *
     * <p>
     * This operation removes the record from persistence if a match is found.
     * It must be executed within a transactional context.
     * </p>
     *
     * @param activeTokenKey the composite key identifying the token to delete
     */
    @Modifying
    @Query("DELETE FROM ActiveTokenEntity at WHERE at.activeTokenKey = :targetKey")
    void deleteTokenByActiveKey(@Param("targetKey") ActiveTokenKey activeTokenKey);

    /**
     * Deletes all {@link ActiveTokenEntity} entries belonging to a subject.
     *
     * <p>
     * This is a bulk delete operation and must be executed inside a transactional
     * service method. Use with caution as it will remove all active tokens for
     * the given subject.
     * </p>
     *
     * @param subject the subject identifier (UUID) whose tokens should be deleted
     */
    @Modifying
    @Query("DELETE FROM ActiveTokenEntity at WHERE at.activeTokenKey.subject = :targetSubject")
    void deleteTokensBySubject(@Param("targetSubject") UUID subject);
}
