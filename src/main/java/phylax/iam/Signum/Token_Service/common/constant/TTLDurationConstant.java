package phylax.iam.Signum.Token_Service.common.constant;

import phylax.iam.Signum.Token_Service.common.util.time.TimeConvertorUtil;

/**
 * Enumeration of default time-to-live (TTL) durations for different token types.
 *
 * <p>
 * Each constant represents the standard lifetime of a token in seconds,
 * calculated using {@link TimeConvertorUtil}. These defaults are intended
 * to balance security with usability, following common industry practices.
 * </p>
 *
 * <h2>Defaults</h2>
 * <ul>
 *   <li>{@link #TEMPORARY_TOKEN} – 7 days (in seconds)</li>
 *   <li>{@link #REFRESH_TOKEN} – 5 minutes (in seconds)</li>
 *   <li>{@link #AUTHENTICATION_TOKEN} – 15 minutes (in seconds)</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * int authTokenTTL = TTLDurationConstant.AUTHENTICATION_TOKEN.getSeconds();
 * }</pre>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
public enum TTLDurationConstant {

    /** Default lifetime for temporary tokens (7 days). */
    TEMPORARY_TOKEN(TimeConvertorUtil.daysToSeconds(7)),

    /** Default lifetime for refresh tokens (5 minutes). */
    REFRESH_TOKEN(TimeConvertorUtil.minutesToSeconds(5)),

    /** Default lifetime for authentication tokens (15 minutes). */
    AUTHENTICATION_TOKEN(TimeConvertorUtil.minutesToSeconds(15));

    /** Lifetime of the token type in seconds. */
    private final int seconds;

    TTLDurationConstant(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Returns the TTL duration in seconds.
     *
     * @return the TTL value in seconds
     */
    public int getSeconds() {
        return this.seconds;
    }
}

