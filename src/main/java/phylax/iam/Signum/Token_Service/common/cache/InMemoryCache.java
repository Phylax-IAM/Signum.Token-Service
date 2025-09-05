package phylax.iam.Signum.Token_Service.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import phylax.iam.Signum.Token_Service.common.collection.LRUCache;
import phylax.iam.Signum.Token_Service.common.contract.PersistableContract;
import phylax.iam.Signum.Token_Service.common.util.logging.LoggerUtil;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the {@link PersistableContract} interface backed by a
 * thread-safe {@link ConcurrentHashMap}.
 * <p>
 * This implementation is useful for testing, prototyping, or lightweight use cases
 * where persistence across application restarts is not required.
 * </p>
 *
 * @param <K> the type of the key used to identify a persisted value
 * @param <V> the type of the value being persisted
 */
@Component
public final class InMemoryCache<K, V> implements PersistableContract<K, V> {

    /**
     * Internal cache for storing key-value pairs in memory.
     */
    @Autowired
    private LRUCache<K, V> lruCache;

    private static final Logger logger = LoggerFactory.getLogger(InMemoryCache.class);

    /**
     * Retrieves the value associated with the given key from memory.
     *
     * @param key the identifier of the value to retrieve; must not be {@code null}
     * @return an {@link Optional} containing the value if present,
     *         or {@link Optional#empty()} if no value is mapped to the key
     */
    @Override
    public Optional<V> read(K key) {
        LoggerUtil.logInfo(logger, "Cache Hit - retrieving data from in memory cache");
        return Optional.ofNullable(this.lruCache.getOrDefault(key, null));
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
    public void write(K key, V value) {
        this.lruCache.put(key, value);
        LoggerUtil.logInfo(logger, "Cache Miss - saving data to in memory cache");

    }

    /**
     * Removes the value associated with the specified key from memory.
     * <p>
     * If the key does not exist, this method has no effect.
     * This method delegates the removal to the underlying {@link LRUCache}.
     * </p>
     *
     * @param key the key of the entry to remove; must not be {@code null}
     */
    @Override
    public void delete(K key) {
        this.lruCache.delete(key);
        LoggerUtil.logInfo(logger, "Cache Evict - data removed from in memory cache");
    }
}
