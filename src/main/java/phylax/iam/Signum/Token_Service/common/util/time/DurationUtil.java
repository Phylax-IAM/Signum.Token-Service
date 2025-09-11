package phylax.iam.Signum.Token_Service.common.util.time;

import phylax.iam.Signum.Token_Service.common.constant.TTLDurationConstant;
import phylax.iam.Signum.Token_Service.common.constant.TokenClassConstant;
import phylax.iam.Signum.Token_Service.common.exception.IllegalDurationException;
import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;

/**
 * Utility class for resolving effective TTL (time-to-live) durations
 * for different token classes.
 *
 * <p>
 * This class enforces duration validation and provides default values
 * from {@link TTLDurationConstant} when explicit values are not supplied.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Validate that a custom duration is non-negative.</li>
 *   <li>Use a custom value if provided (> 0).</li>
 *   <li>Fallback to default durations if no custom value is specified.</li>
 * </ul>
 *
 * <h2>Defaults</h2>
 * <ul>
 *   <li>{@link TokenClassConstant#REFRESH} → {@link TTLDurationConstant#REFRESH_TOKEN}</li>
 *   <li>{@link TokenClassConstant#TEMPORARY} → {@link TTLDurationConstant#TEMPORARY_TOKEN}</li>
 *   <li>{@link TokenClassConstant#AUTHENTICATION} → {@link TTLDurationConstant#AUTHENTICATION_TOKEN}</li>
 * </ul>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * int ttl = DurationUtil.getDurationOrDefault(0, TokenClassConstant.AUTHENTICATION);
 * // returns 900 (15 minutes) by default
 * }</pre>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
public final class DurationUtil {

    private DurationUtil() {
        throw new IllegalInstantiationException();
    }

    /**
     * Resolves the effective duration for a given token class.
     *
     * @param seconds    the requested duration in seconds; if {@code > 0}, it is used as-is
     * @param tokenClass the type of token for which the duration applies
     * @return the resolved duration in seconds
     * @throws IllegalDurationException if {@code seconds} is negative
     */
    public static int getDurationOrDefault(int seconds, TokenClassConstant tokenClass) {
        if (seconds < 0) {
            throw new IllegalDurationException();
        }

        if (seconds > 0) {
            return seconds;
        }

        return switch (tokenClass) {
            case REFRESH -> TTLDurationConstant.REFRESH_TOKEN.getSeconds();
            case TEMPORARY -> TTLDurationConstant.TEMPORARY_TOKEN.getSeconds();
            default -> TTLDurationConstant.AUTHENTICATION_TOKEN.getSeconds();
        };
    }
}

