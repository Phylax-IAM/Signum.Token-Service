package phylax.iam.Signum.Token_Service.common.util.time;

import phylax.iam.Signum.Token_Service.common.exception.IllegalDurationException;
import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;
import phylax.iam.Signum.Token_Service.common.exception.OutOfLimitConversionException;

/**
 * Utility class for safely converting between different time units
 * (days, hours, minutes, seconds).
 *
 * <p>
 * This class validates input durations and checks for potential integer
 * overflow before performing conversions. It ensures that all results
 * remain within the range of {@link Integer#MAX_VALUE}.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Validate that durations are non-negative.</li>
 *   <li>Prevent arithmetic overflow during conversions.</li>
 *   <li>Provide convenience methods for converting between days,
 *       hours, minutes, and seconds.</li>
 * </ul>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * int seconds = TimeConvertorUtil.minutesToSeconds(15); // 900
 * int hours = TimeConvertorUtil.daysToHours(2);         // 48
 * }</pre>
 *
 * <h2>Exceptions</h2>
 * <ul>
 *   <li>{@link IllegalDurationException} – if a negative duration is provided.</li>
 *   <li>{@link OutOfLimitConversionException} – if the conversion result
 *       would exceed {@link Integer#MAX_VALUE}.</li>
 * </ul>
 *
 * <p>
 * This class cannot be instantiated and should only be accessed via
 * its static utility methods.
 * </p>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
public final class TimeConvertorUtil {

    private TimeConvertorUtil() {
        throw new IllegalInstantiationException();
    }

    /**
     * Validates that the given duration is non-negative.
     *
     * @param duration the duration value to check
     * @throws IllegalDurationException if {@code duration} is negative
     */
    public static void validateDuration(int duration) {
        if (duration < 0) {
            throw new IllegalDurationException();
        }
    }

    /**
     * Converts minutes into seconds.
     *
     * @param minutes the number of minutes to convert
     * @return the equivalent seconds
     * @throws IllegalDurationException if {@code minutes} is negative
     * @throws OutOfLimitConversionException if the result would exceed {@link Integer#MAX_VALUE}
     */
    public static int minutesToSeconds(int minutes) {
        validateDuration(minutes);
        if (minutes >= (Integer.MAX_VALUE / 60)) {
            throw new OutOfLimitConversionException();
        }
        return minutes * 60;
    }

    /**
     * Converts hours into minutes.
     *
     * @param hours the number of hours to convert
     * @return the equivalent minutes
     * @throws IllegalDurationException if {@code hours} is negative
     * @throws OutOfLimitConversionException if the result would exceed {@link Integer#MAX_VALUE}
     */
    public static int hoursToMinutes(int hours) {
        validateDuration(hours);
        if (hours >= (Integer.MAX_VALUE / 60)) {
            throw new OutOfLimitConversionException();
        }
        return hours * 60;
    }

    /**
     * Converts hours into seconds.
     *
     * @param hours the number of hours to convert
     * @return the equivalent seconds
     * @throws IllegalDurationException if {@code hours} is negative
     * @throws OutOfLimitConversionException if the result would exceed {@link Integer#MAX_VALUE}
     */
    public static int hoursToSeconds(int hours) {
        validateDuration(hours);
        final int minutes = hoursToMinutes(hours);
        return minutesToSeconds(minutes);
    }

    /**
     * Converts days into hours.
     *
     * @param days the number of days to convert
     * @return the equivalent hours
     * @throws IllegalDurationException if {@code days} is negative
     * @throws OutOfLimitConversionException if the result would exceed {@link Integer#MAX_VALUE}
     */
    public static int daysToHours(int days) {
        validateDuration(days);
        if (days >= (Integer.MAX_VALUE / 24)) {
            throw new OutOfLimitConversionException();
        }
        return days * 24;
    }

    /**
     * Converts days into minutes.
     *
     * @param days the number of days to convert
     * @return the equivalent minutes
     * @throws IllegalDurationException if {@code days} is negative
     * @throws OutOfLimitConversionException if the result would exceed {@link Integer#MAX_VALUE}
     */
    public static int daysToMinutes(int days) {
        validateDuration(days);
        final int hours = daysToHours(days);
        return hoursToMinutes(hours);
    }

    /**
     * Converts days into seconds.
     *
     * @param days the number of days to convert
     * @return the equivalent seconds
     * @throws IllegalDurationException if {@code days} is negative
     * @throws OutOfLimitConversionException if the result would exceed {@link Integer#MAX_VALUE}
     */
    public static int daysToSeconds(int days) {
        validateDuration(days);
        final int hours = daysToHours(days);
        final int minutes = hoursToMinutes(hours);
        return minutesToSeconds(minutes); // ✅ fixed
    }
}

