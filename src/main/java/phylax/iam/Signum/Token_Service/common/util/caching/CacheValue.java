package phylax.iam.Signum.Token_Service.common.util.caching;

/**
 * A polymorphic wrapper class for storing and retrieving arbitrary values in a type-safe manner.
 * <p>
 * This class is useful in scenarios such as generic caching, where the stored values
 * may be of different types but need to be retrieved with type validation at runtime.
 * </p>
 *
 * <h3>Example Usage:</h3>
 * <pre>{@code
 * CacheValue cacheValue = new CacheValue(UUID.randomUUID());
 *
 * // Retrieve value as UUID
 * UUID uuid = cacheValue.as(UUID.class);
 *
 * // Attempting to retrieve as String will throw an IllegalArgumentException
 * String str = cacheValue.as(String.class); // throws exception
 * }</pre>
 */
public class CacheValue {

    /**
     * The underlying stored value.
     * <p>
     * It can be of any type, and is wrapped inside this class for controlled
     * type-safe retrieval.
     * </p>
     */
    private final Object value;

    /**
     * Constructs a new {@code CacheValue} wrapper around the provided object.
     *
     * @param value the object to wrap; may be {@code null}
     */
    public CacheValue(Object value) {
        this.value = value;
    }

    /**
     * Retrieves the wrapped value as the specified type.
     * <p>
     * This method performs a runtime type check using {@link Class#isInstance(Object)}.
     * If the wrapped value is not of the requested type, an {@link IllegalArgumentException}
     * will be thrown.
     * </p>
     *
     * @param <T>  the target type to cast to
     * @param type the {@link Class} object representing the expected type
     * @return the wrapped value cast to the requested type
     * @throws IllegalArgumentException if the wrapped value is not assignable to {@code type}
     */
    @SuppressWarnings("unchecked")
    public <T> T as(Class<T> type) {
        if (!type.isInstance(value)) {
            throw new IllegalArgumentException("Value is not of the type " + type.getName());
        }
        return (T) value;
    }
}

