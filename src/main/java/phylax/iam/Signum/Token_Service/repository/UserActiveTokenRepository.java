package phylax.iam.Signum.Token_Service.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import phylax.iam.Signum.Token_Service.entity.key.ActiveTokenKey;
import phylax.iam.Signum.Token_Service.entity.UserActiveTokenEntity;


@Repository
public interface UserActiveTokenRepository extends JpaRepository<UserActiveTokenEntity, ActiveTokenKey> {

    // Read
    Optional<UserActiveTokenEntity> findByActiveTokenKey(ActiveTokenKey activeTokenKey);

    // Update

    // Delete
}
