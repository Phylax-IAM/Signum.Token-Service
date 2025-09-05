package phylax.iam.Signum.Token_Service.common.contract;

import java.util.Optional;

/**
 * Contract defining basic cache operations for storing, retrieving,
 * and deleting key-value pairs.
 * <p>
 * Implementations of this contract may use different caching mechanisms,
 * such as in-memory caches, Redis, or other persistent stores. Thread-safety,
 * expiration, and eviction policies are implementation-specific.
 * </p>
 */
public interface CacheServiceContract<K, V> {

    /**
     * Saves the given value in the cache associated with the specified key.
     * <p>
     * If a value already exists for the key, it may be overwritten depending
     * on the implementation.
     * </p>
     *
     * @param key   the key under which to store the value; must not be {@code null}
     * @param value the value to store; must not be {@code null}
     */
    void saveValue(K key, V value);

    /**
     * Retrieves the value associated with the specified key from the cache.
     *
     * @param key the key whose associated value is to be returned; must not be {@code null}
     * @return Optional of the value associated with the key
     */
    Optional<V> getValue(K key);

    /**
     * Deletes the value associated with the specified key from the cache.
     *
     * @param key the key whose entry should be removed; must not be {@code null}
     */
    void deleteValue(K key);
}

