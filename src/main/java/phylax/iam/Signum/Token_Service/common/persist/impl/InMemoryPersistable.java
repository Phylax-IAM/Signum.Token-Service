package phylax.iam.Signum.Token_Service.common.persist.impl;

import phylax.iam.Signum.Token_Service.common.collection.LRUCache;
import phylax.iam.Signum.Token_Service.common.persist.Persistable;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the {@link Persistable} interface backed by a
 * thread-safe {@link ConcurrentHashMap}.
 * <p>
 * This implementation is useful for testing, prototyping, or lightweight use cases
 * where persistence across application restarts is not required.
 * </p>
 *
 * @param <T> the type of the key used to identify a persisted value
 * @param <R> the type of the value being persisted
 */
public final class InMemoryPersistable<T, R> implements Persistable<T, R> {

    /**
     * Internal cache for storing key-value pairs in memory.
     */
    private final LRUCache<T, R> lruCache = new LRUCache<>();

    /**
     * Retrieves the value associated with the given key from memory.
     *
     * @param key the identifier of the value to retrieve; must not be {@code null}
     * @return an {@link Optional} containing the value if present,
     *         or {@link Optional#empty()} if no value is mapped to the key
     */
    @Override
    public Optional<R> read(T key) {
        return Optional.ofNullable(lruCache.getOrDefault(key, null));
    }

    /**
     * Persists the given value in memory under the specified key.
     * <p>
     * If the key already exists, its value will be overwritten.
     * </p>
     *
     * @param key   the identifier under which to persist the value; must not be {@code null}
     * @param value the value to persist; must not be {@code null}
     */
    @Override
    public void write(T key, R value) {
        lruCache.put(key, value);
    }
}
