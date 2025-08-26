package phylax.iam.Signum.Token_Service.common.util.id;

import com.github.f4b6a3.uuid.UuidCreator;
import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;

/**
 * Utility class for generating UUID values in different formats.
 * <p>
 * Currently provides support for generating {@code UUIDv7}, which is
 * a time-ordered UUID variant. UUIDv7 offers improved database indexing
 * performance compared to random UUIDs due to its time-based ordering.
 * </p>
 * <p>
 * This class is non-instantiable and intended only for static access.
 * </p>
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
     * Generates a new {@code UUIDv7} string.
     * <p>
     * UUIDv7 is a time-ordered UUID defined in
     * <a href="https://datatracker.ietf.org/doc/html/draft-ietf-uuidrev-rfc4122bis">
     * the IETF draft for UUID revision</a>.
     * It encodes the current Unix epoch timestamp and ensures
     * k-sortability for efficient database operations.
     * </p>
     *
     * @return a string representation of a newly generated {@code UUIDv7}
     */
    public static String generateUUIDv7() {
        return UuidCreator.getTimeOrderedEpoch().toString();
    }

}
