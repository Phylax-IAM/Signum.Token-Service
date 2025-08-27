package phylax.iam.Signum.Token_Service.common.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import phylax.iam.Signum.Token_Service.common.constant.SecretAlgorithmConstant;
import phylax.iam.Signum.Token_Service.common.constant.SecretKeyTypeConstant;
import phylax.iam.Signum.Token_Service.common.persist.Persistable;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import phylax.iam.Signum.Token_Service.common.util.secret.SecretUtil;

/**
 * Utility class for generating, encoding, and decoding {@link SecretKey} instances.
 *
 * <p>This class simplifies the management of symmetric keys by providing methods to:</p>
 * <ul>
 *   <li>Generate new cryptographic keys of a specified size and type.</li>
 *   <li>Convert keys to and from Base64 strings for storage or transmission.</li>
 *   <li>Fetch an existing key from environment variables, or generate a new one if none is found.</li>
 * </ul>
 *
 * <p><strong>Security Note:</strong> Keys must be handled with care.
 * For production systems, consider secure key storage solutions (e.g.,
 * Java KeyStore, AWS KMS, HashiCorp Vault) instead of environment variables.</p>
 *
 * @see javax.crypto.SecretKey
 * @see javax.crypto.KeyGenerator
 * @see javax.crypto.spec.SecretKeySpec
 */
@Component
public final class SecretKeyGenerator {

    /**
     * Abstraction for persisting generated keys.
     * <p>
     * The first type parameter represents the key identifier (e.g., a name or alias),
     * while the second represents the serialized form of the secret (e.g., Base64 string).
     * </p>
     */
    private final Persistable<SecretKeyTypeConstant, String> persistable;

    /**
     * Creates a new {@code SecretKeyGenerator} with the given persistence mechanism.
     *
     * @param persistable the persistence abstraction used for storing and retrieving keys;
     *                    must not be {@code null}
     */
    public SecretKeyGenerator(@Qualifier("inMemoryPersistable") Persistable<SecretKeyTypeConstant, String> persistable) {
        this.persistable = persistable;
    }

    /**
     * Generates a new cryptographic {@link SecretKey}.
     *
     * @param keySize the key size in bits (e.g., 128, 192, 256 for AES)
     * @param type the key algorithm (e.g., "AES", "HmacSHA256")
     * @return the newly generated {@link SecretKey}
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     */
    public SecretKey generateKey(int keySize, String type) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(type);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

    /**
     * Fetches a {@link SecretKey} from an environment variable, or generates a new one if not found.
     *
     * <p>If the environment variable with the given {@code secretKeyName} exists,
     * its value is assumed to be a Base64-encoded key and is reconstructed.
     * Otherwise, a new key is generated.</p>
     *
     * @param secretKeyName the name of the environment variable containing the Base64 key
     * @param keySize the key size in bits (used if a new key is generated)
     * @param type the key algorithm (e.g., "AES", "HmacSHA256")
     * @return the fetched or newly generated {@link SecretKey}
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     */
    public SecretKey fetchOrGenerateKey(SecretKeyTypeConstant secretKeyName, int keySize, String type) throws NoSuchAlgorithmException {
        Optional<String> secretKeyString = persistable.read(secretKeyName);
        SecretKey secretKey;

        if(secretKeyString.isEmpty()) {
            secretKey = generateKey(keySize, type);
            persistable.write(secretKeyName, SecretUtil.toBase64String(secretKey));
        } else {
            secretKey = SecretUtil.fromBase64String(secretKeyString.get(), type);
        }
        return secretKey;
    }
}
