package phylax.iam.Signum.Token_Service.common.util.id;

import java.util.UUID;
import com.github.f4b6a3.uuid.UuidCreator;

import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;
import phylax.iam.Signum.Token_Service.common.exception.UUIDException;


/**
 * Utility class for generating and validating UUID values in different formats.
 * <p>
 * Currently provides support for generating and verifying {@code UUIDv7}, which is
 * a time-ordered UUID variant. UUIDv7 offers improved database indexing
 * performance compared to random UUIDs due to its time-based ordering.
 * </p>
 *
 * <p>
 * This class is non-instantiable and intended only for static access.
 * All methods are thread-safe as they rely on immutable {@link UUID} objects.
 * </p>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
public final class UUIDUtil {

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws IllegalInstantiationException always, since this class
     *                                       is a static utility holder
     */
    private UUIDUtil() {
        throw new IllegalInstantiationException();
    }

    /**
     * Converts a string into a {@link UUID} object.
     *
     * @param uuidString the UUID string to parse
     * @return a {@link UUID} parsed from the given string
     * @throws UUIDException if the string is not a valid UUID representation
     */
    public static UUID extractUUIDFromString(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new UUIDException("This is not a valid UUID String");
        }
    }

    /**
     * Verifies whether a given string represents a UUID of the specified version.
     *
     * @param uuidString    the UUID string to check
     * @param targetVersion the expected UUID version (e.g., 7 for UUIDv7)
     * @return {@code true} if the string is a valid UUID of the given version,
     *         {@code false} otherwise
     */
    public static boolean verifyIsStringUUIDv7(String uuidString, int targetVersion) {
        UUID uuid;
        try {
            uuid = extractUUIDFromString(uuidString);
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
        return uuid.version() == targetVersion;
    }

    /**
     * Verifies whether the given {@link UUID} is of the specified version.
     *
     * @param targetUUID    the UUID to check
     * @param targetVersion the expected version (e.g., 7 for UUIDv7)
     * @return {@code true} if the UUID matches the version, {@code false} otherwise
     */
    public static boolean verifyIsUUIDv7(UUID targetUUID, int targetVersion) {
        return targetUUID.version() == targetVersion;
    }

    /**
     * Generates a new {@code UUIDv7}.
     * <p>
     * UUIDv7 is a time-ordered UUID defined in
     * <a href="https://datatracker.ietf.org/doc/html/draft-ietf-uuidrev-rfc4122bis">
     * the IETF draft for UUID revision</a>.
     * It encodes the current Unix epoch timestamp and ensures
     * k-sortability for efficient database operations.
     * </p>
     *
     * @return a newly generated {@link UUID} instance of version 7
     */
    public static UUID generateUUIDv7() {
        return UuidCreator.getTimeOrderedEpoch();
    }

    /**
     * Generates a new {@code UUIDv7} string.
     * <p>
     * This method is a convenience wrapper around {@link #generateUUIDv7()},
     * returning the UUID directly as its standard string representation.
     * </p>
     *
     * @return a string representation of a newly generated {@code UUIDv7}
     */
    public static String generateUUIDv7AsString() {
        return generateUUIDv7().toString();
    }
}

