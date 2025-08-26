package phylax.iam.Signum.Token_Service.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import phylax.iam.Signum.Token_Service.entity.key.RevokedTokenKey;
import phylax.iam.Signum.Token_Service.entity.UserRevokedTokenEntity;


@Repository
public interface UserRevokedTokenRepository extends JpaRepository<UserRevokedTokenEntity, RevokedTokenKey> {

    // Read
    Optional<UserRevokedTokenEntity> findByRevokedTokenKey(RevokedTokenKey revokedTokenKey);

    // Delete
    @Query("DELETE FROM UserRevokedTokenEntity RT WHERE RT.expiresAt < :now")
    void deleteExpiredToken(@Param("now") Instant thisInstant);
}
