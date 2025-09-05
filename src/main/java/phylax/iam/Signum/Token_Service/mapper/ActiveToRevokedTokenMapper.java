package phylax.iam.Signum.Token_Service.mapper;

import phylax.iam.Signum.Token_Service.entity.key.RevokedTokenKey;
import phylax.iam.Signum.Token_Service.entity.UserActiveTokenEntity;
import phylax.iam.Signum.Token_Service.entity.UserRevokedTokenEntity;
import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Utility class for mapping {@link UserActiveTokenEntity} instances
 * into {@link UserRevokedTokenEntity} instances.
 * <p>
 * This mapper is typically used when tokens are revoked, ensuring
 * that active token records can be efficiently transformed into their
 * revoked counterparts for persistence and audit purposes.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *     <li>Convert a single {@link UserActiveTokenEntity} into a {@link UserRevokedTokenEntity}.</li>
 *     <li>Convert a list of active tokens into a list of revoked tokens using parallel streams.</li>
 *     <li>Ensure that mapping logic remains centralized and reusable.</li>
 * </ul>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * UserActiveTokenEntity activeToken = ...;
 * UserRevokedTokenEntity revokedToken =
 *     ActiveToRevokedTokenMapper.toRevokedTokenEntity(activeToken);
 *
 * List<UserRevokedTokenEntity> revokedTokens =
 *     ActiveToRevokedTokenMapper.toRevokedTokenEntityList(activeTokenList);
 * }</pre>
 *
 * <p>
 * This class cannot be instantiated and should only be used
 * via its static utility methods.
 * </p>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
public final class ActiveToRevokedTokenMapper {

    private ActiveToRevokedTokenMapper() {
        throw new IllegalInstantiationException();
    }

    /**
     * Converts a single {@link UserActiveTokenEntity} into its
     * corresponding {@link UserRevokedTokenEntity}.
     *
     * @param userActiveTokenEntity the active token entity to map
     * @return a new {@link UserRevokedTokenEntity} containing the
     *         subject, token, class, and expiration details
     */
    public static UserRevokedTokenEntity toRevokedTokenEntity(UserActiveTokenEntity userActiveTokenEntity) {
        return UserRevokedTokenEntity
                .builder()
                .revokedTokenKey(
                        RevokedTokenKey
                                .builder()
                                .subject(userActiveTokenEntity.getActiveTokenKey().getSubject())
                                .tokenId(userActiveTokenEntity.getTokenId())
                                .build()
                )
                .token(userActiveTokenEntity.getToken())
                .tokenClassConstant(userActiveTokenEntity.getActiveTokenKey().getTokenClassConstant())
                .expiresAt(userActiveTokenEntity.getExpiresAt())
                .build();
    }

    /**
     * Converts a list of {@link UserActiveTokenEntity} objects
     * into a list of {@link UserRevokedTokenEntity} objects.
     * <p>
     * Uses a parallel stream for improved performance on large lists.
     * </p>
     *
     * @param userActiveTokenEntityList the list of active token entities to map
     * @return a list of revoked token entities corresponding to the input list
     */
    public static List<UserRevokedTokenEntity> toRevokedTokenEntityList(List<UserActiveTokenEntity> userActiveTokenEntityList) {
        return userActiveTokenEntityList
                .stream()
                .map(ActiveToRevokedTokenMapper::toRevokedTokenEntity)
                .collect(Collectors.toList());
    }
}
