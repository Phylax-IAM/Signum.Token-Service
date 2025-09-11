package phylax.iam.Signum.Token_Service.config.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import phylax.iam.Signum.Token_Service.common.constant.SecretAlgorithmConstant;
import phylax.iam.Signum.Token_Service.common.constant.SecretKeyTypeConstant;
import phylax.iam.Signum.Token_Service.common.exception.InternalServerException;
import phylax.iam.Signum.Token_Service.common.exception.NoSuchSecretKeyException;
import phylax.iam.Signum.Token_Service.common.security.SecretKeyGenerator;
import phylax.iam.Signum.Token_Service.common.util.logging.LoggerUtil;
import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration component responsible for managing and caching cryptographic secret keys.
 *
 * <p>
 * This component centralizes the creation and retrieval of secret keys used in token
 * signing and encryption operations within the system. It initializes a local cache
 * of keys at startup, ensuring that the correct key sizes and algorithms are applied
 * for each token type.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Generate or fetch symmetric keys for token operations (HMAC, AES-GCM).</li>
 *   <li>Cache keys in memory to avoid repeated regeneration.</li>
 *   <li>Provide a centralized API for retrieving keys by their
 *       {@link SecretKeyTypeConstant} type.</li>
 * </ul>
 *
 * <h2>Supported Keys</h2>
 * <ul>
 *   <li>{@link SecretKeyTypeConstant#CIPHER_SECRET_KEY} → AES-GCM (128-bit)</li>
 *   <li>{@link SecretKeyTypeConstant#TEMP_SECRET_KEY} → HMAC-SHA256 (256-bit)</li>
 *   <li>{@link SecretKeyTypeConstant#AUTH_SECRET_KEY} → HMAC-SHA256 (256-bit)</li>
 *   <li>{@link SecretKeyTypeConstant#REFRESH_SECRET_KEY} → HMAC-SHA256 (256-bit)</li>
 * </ul>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * SecretKeyConfig keyConfig = ...
 * SecretKey authKey = keyConfig.get(SecretKeyTypeConstant.AUTH_SECRET_KEY);
 * }</pre>
 *
 * <p>
 * If a key cannot be found, a {@link NoSuchSecretKeyException} will be thrown.
 * </p>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
@Component
public class SecretKeyConfig {

    /** Default key size (in bits) for HMAC-SHA256 signing keys. */
    private final int hmacKeySize = 256;

    /** Default key size (in bits) for AES-GCM cipher keys. */
    private final int cipherKeySize = 128;

    /** Service responsible for fetching or generating secret keys. */
    @Autowired
    private SecretKeyGenerator secretKeyGenerator;

    /** Logger instance for error reporting. */
    private final Logger logger = LoggerFactory.getLogger(SecretKeyConfig.class);

    /** In-memory cache of secret keys, indexed by their type. */
    private final Map<SecretKeyTypeConstant, SecretKey> secretKeyMap = new HashMap<>();

    /**
     * Constructs a new {@code SecretKeyConfig} and initializes the in-memory key cache.
     * <p>
     * At startup, this method preloads all required secret keys, ensuring they are
     * available for subsequent cryptographic operations.
     * </p>
     */
    public SecretKeyConfig() {
        this.initKeyMap();
    }

    /**
     * Initializes the internal key cache by fetching or generating keys
     * for all supported {@link SecretKeyTypeConstant} values.
     *
     * <p>
     * If any key generation or retrieval fails, the error is logged and
     * an {@link InternalServerException} is thrown.
     * </p>
     */
    private void initKeyMap() {
        try {
            this.secretKeyMap.put(
                    SecretKeyTypeConstant.CIPHER_SECRET_KEY,
                    secretKeyGenerator.fetchOrGenerateKey(
                            SecretKeyTypeConstant.CIPHER_SECRET_KEY,
                            cipherKeySize,
                            SecretAlgorithmConstant.AES_ALGO.getAlgorithm()
                    )
            );
            this.secretKeyMap.put(
                    SecretKeyTypeConstant.TEMP_SECRET_KEY,
                    secretKeyGenerator.fetchOrGenerateKey(
                            SecretKeyTypeConstant.TEMP_SECRET_KEY,
                            hmacKeySize,
                            SecretAlgorithmConstant.HMAC_SHA256.getAlgorithm()
                    )
            );
            this.secretKeyMap.put(
                    SecretKeyTypeConstant.AUTH_SECRET_KEY,
                    secretKeyGenerator.fetchOrGenerateKey(
                            SecretKeyTypeConstant.AUTH_SECRET_KEY,
                            hmacKeySize,
                            SecretAlgorithmConstant.HMAC_SHA256.getAlgorithm()
                    )
            );
            this.secretKeyMap.put(
                    SecretKeyTypeConstant.REFRESH_SECRET_KEY,
                    secretKeyGenerator.fetchOrGenerateKey(
                            SecretKeyTypeConstant.REFRESH_SECRET_KEY,
                            hmacKeySize,
                            SecretAlgorithmConstant.HMAC_SHA256.getAlgorithm()
                    )
            );

        } catch (Exception e) {
            LoggerUtil.logErrorAndDebug(logger, "", e);
            throw new InternalServerException(e.getMessage());
        }
    }

    /**
     * Retrieves a secret key from the cache by its type.
     *
     * <p>
     * If no key is available for the specified type, a
     * {@link NoSuchSecretKeyException} is thrown.
     * </p>
     *
     * @param secretKeyTypeConstant the type of key to retrieve
     * @return the {@link SecretKey} associated with the specified type
     * @throws NoSuchSecretKeyException if no key exists for the given type
     */
    public SecretKey get(SecretKeyTypeConstant secretKeyTypeConstant) {
        try {
            return this.secretKeyMap.get(secretKeyTypeConstant);
        } catch (Exception e) {
            LoggerUtil.logErrorAndDebug(logger, "", e);
            throw new NoSuchSecretKeyException();
        }
    }
}

