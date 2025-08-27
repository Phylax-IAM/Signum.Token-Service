package phylax.iam.Signum.Token_Service.common.collection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Component;

/**
 * A thread-safe Least Recently Used (LRU) cache implementation.
 * <p>
 * This cache maintains a fixed maximum capacity. When the number of entries exceeds
 * {@link #maxCapacity}, the least recently used entry is automatically evicted.
 * <p>
 * Accessing or inserting an entry updates its usage order, ensuring that frequently
 * accessed entries remain in the cache while older, less used entries are evicted first.
 * </p>
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
@Component
public class LRUCache<K, V> {

    /** Maximum number of entries the cache can hold. */
    private final int maxCapacity = 40;

    /** Internal storage for cache entries. */
    private final ConcurrentHashMap<K, V> cacheMap = new ConcurrentHashMap<>();

    /** Queue to track access order for LRU eviction. */
    private final LinkedBlockingDeque<K> accessOrderQueue = new LinkedBlockingDeque<>();

    /**
     * Removes the eldest entries if the cache exceeds its maximum capacity.
     * <p>
     * This method is called after every insertion to maintain the LRU property.
     * </p>
     */
    private void verifyAndEvict() {
        while (this.accessOrderQueue.size() > this.maxCapacity) {
            K eldest = this.accessOrderQueue.pollFirst();
            if (eldest != null) {
                this.cacheMap.remove(eldest);
            }
        }
    }

    /**
     * Updates the access order of the given key.
     * <p>
     * If the key exists, it is moved to the end of the access order queue to mark it
     * as recently used.
     * </p>
     *
     * @param key the key whose access order should be updated
     */
    private void reorder(K key) {
        this.accessOrderQueue.remove(key);
        this.accessOrderQueue.addLast(key);
    }

    /**
     * Inserts or updates the value for the given key in the cache.
     * <p>
     * This method updates the access order of the key and evicts the least recently
     * used entry if the cache exceeds its maximum capacity.
     * </p>
     *
     * @param key   the key to insert or update
     * @param value the value associated with the key
     */
    public synchronized void put(K key, V value) {
        this.reorder(key);
        this.cacheMap.put(key, value);
        this.verifyAndEvict();
    }

    /**
     * Retrieves the value associated with the given key, updating its usage order.
     * <p>
     * If the key exists in the cache, it is marked as recently used.
     * Returns {@code null} if the key is not present.
     * </p>
     *
     * @param key the key whose value is to be retrieved
     * @return the value associated with the key, or {@code null} if not present
     */
    public synchronized V get(K key) {
        if (this.cacheMap.containsKey(key)) {
            this.reorder(key);
            return this.cacheMap.get(key);
        }
        return null;
    }

    /**
     * Retrieves the value associated with the given key, or returns a default value
     * if the key is not present.
     * <p>
     * This method does not update the usage order.
     * </p>
     *
     * @param key          the key whose value is to be retrieved
     * @param defaultValue the default value to return if the key is not present
     * @return the value associated with the key, or {@code defaultValue} if not present
     */
    public synchronized V getOrDefault(K key, V defaultValue) {
        return this.cacheMap.getOrDefault(key, defaultValue);
    }
}
