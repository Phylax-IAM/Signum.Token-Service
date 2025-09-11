package phylax.iam.Signum.Token_Service.mapper;

import phylax.iam.Signum.Token_Service.entity.ActiveTokenEntity;
import phylax.iam.Signum.Token_Service.entity.RevokedTokenEntity;
import phylax.iam.Signum.Token_Service.entity.key.RevokedTokenKey;
import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Utility class for mapping {@link ActiveTokenEntity} instances
 * into {@link RevokedTokenEntity} instances.
 * <p>
 * This mapper is typically used when tokens are revoked, ensuring
 * that active token records can be efficiently transformed into their
 * revoked counterparts for persistence and audit purposes.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *     <li>Convert a single {@link ActiveTokenEntity} into a {@link RevokedTokenEntity}.</li>
 *     <li>Convert a list of active tokens into a list of revoked tokens using parallel streams.</li>
 *     <li>Ensure that mapping logic remains centralized and reusable.</li>
 * </ul>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * ActiveTokenEntity activeToken = ...;
 * RevokedTokenEntity revokedToken =
 *     ActiveToRevokedTokenMapper.toRevokedTokenEntity(activeToken);
 *
 * List<RevokedTokenEntity> revokedTokens =
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
     * Converts a single {@link ActiveTokenEntity} into its
     * corresponding {@link RevokedTokenEntity}.
     *
     * @param activeTokenEntity the active token entity to map
     * @return a new {@link RevokedTokenEntity} containing the
     *         subject, token, class, and expiration details
     */
    public static RevokedTokenEntity toRevokedTokenEntity(ActiveTokenEntity activeTokenEntity) {
        return RevokedTokenEntity
                .builder()
                .revokedTokenKey(
                        RevokedTokenKey
                                .builder()
                                .subject(activeTokenEntity.getActiveTokenKey().getSubject())
                                .tokenId(activeTokenEntity.getTokenId())
                                .build()
                )
                .token(activeTokenEntity.getToken())
                .tokenClassConstant(activeTokenEntity.getActiveTokenKey().getTokenClassConstant())
                .expiresAt(activeTokenEntity.getExpiresAt())
                .build();
    }

    /**
     * Converts a list of {@link ActiveTokenEntity} objects
     * into a list of {@link RevokedTokenEntity} objects.
     * <p>
     * Uses a parallel stream for improved performance on large lists.
     * </p>
     *
     * @param activeTokenEntityList the list of active token entities to map
     * @return a list of revoked token entities corresponding to the input list
     */
    public static List<RevokedTokenEntity> toRevokedTokenEntityList(List<ActiveTokenEntity> activeTokenEntityList) {
        return activeTokenEntityList
                .stream()
                .map(ActiveToRevokedTokenMapper::toRevokedTokenEntity)
                .collect(Collectors.toList());
    }
}
