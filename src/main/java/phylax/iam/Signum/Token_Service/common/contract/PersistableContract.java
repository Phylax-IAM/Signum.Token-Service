package phylax.iam.Signum.Token_Service.common.contract;

import java.util.Optional;

/**
 * Generic persistence abstraction for reading, writing, and deleting values
 * associated with a given key.
 * <p>
 * This interface decouples persistence logic from storage details,
 * allowing implementations to back data with various storage mechanisms,
 * such as in-memory maps, databases, files, or cloud secret managers.
 * </p>
 *
 * @param <K> the type of the key used to identify a persisted value
 * @param <V> the type of the value being persisted
 */
public interface PersistableContract<K, V> {

    /**
     * Reads a persisted value associated with the given key.
     *
     * @param key the identifier of the value to read; must not be {@code null}
     * @return an {@link Optional} containing the persisted value if found,
     *         or {@link Optional#empty()} if no value is associated with the key
     */
    Optional<V> read(K key);

    /**
     * Persists the given value under the specified key.
     * <p>
     * If a value is already associated with the key, the implementation
     * may choose to overwrite it or reject the operation depending on
     * the storage semantics.
     * </p>
     *
     * @param key   the identifier under which to persist the value; must not be {@code null}
     * @param value the value to persist; must not be {@code null}
     */
    void write(K key, V value);

    /**
     * Deletes the value associated with the given key.
     * <p>
     * If no value exists for the key, the operation may be a no-op.
     * </p>
     *
     * @param key the identifier of the value to delete; must not be {@code null}
     */
    void delete(K key);
}
