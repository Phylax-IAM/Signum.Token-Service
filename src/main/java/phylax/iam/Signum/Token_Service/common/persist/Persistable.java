package phylax.iam.Signum.Token_Service.common.persist;

import java.util.Optional;

/**
 * Generic persistence abstraction for reading and writing values
 * associated with a given key.
 * <p>
 * This interface decouples persistence logic from storage details,
 * allowing implementations to back data with various storage
 * mechanisms (e.g., in-memory maps, databases, files, or cloud
 * secret managers).
 * </p>
 *
 * @param <T> the type of the key used to identify a persisted value
 * @param <R> the type of the value being persisted
 */
public interface Persistable<T, R> {

    /**
     * Reads a persisted value associated with the given key.
     *
     * @param key the identifier of the value to read; must not be {@code null}
     * @return an {@link Optional} containing the persisted value if found,
     *         or {@link Optional#empty()} if no value is associated with the key
     */
    Optional<R> read(T key);

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
    void write(T key, R value);
}
