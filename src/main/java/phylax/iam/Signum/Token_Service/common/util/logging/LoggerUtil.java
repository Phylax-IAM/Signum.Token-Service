package phylax.iam.Signum.Token_Service.common.util.logging;

import org.slf4j.Logger;

import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;


/**
 * Utility class for logging errors and debug information consistently.
 * <p>
 * This class provides a static method to log an error message and the
 * corresponding stack trace using a given {@link org.slf4j.Logger}.
 * </p>
 * <p>
 * This is a non-instantiable utility class.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * try {
 *     // some code that may throw an interceptor
 * } catch (Exception e) {
 *     LoggerUtil.logErrorAndDebug(logger, "An unexpected error occurred", e);
 * }
 * }</pre>
 *
 * @author Pragyanshu
 * @since 1.0
 */
public final class LoggerUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private LoggerUtil() {
        throw new IllegalInstantiationException();
    }

    /**
     * Logs the provided error message and stack trace using the given logger.
     * <p>
     * If the {@code message} is {@code null}, the interceptor's message will be logged
     * at the ERROR level. The full stack trace is always logged at the DEBUG level.
     * </p>
     *
     * @param logger  the SLF4J logger to use for logging; must not be {@code null}
     * @param message the error message to log; may be {@code null}, in which case
     *                the interceptor's message will be used
     * @param e       the interceptor whose stack trace should be logged; must not be {@code null}
     */
    public static void logErrorAndDebug(Logger logger, String message, Throwable e) {
        final String errorMessage = LogUtil.format(message == null || message.isEmpty() ? e.getMessage() : message);
        logger.error(errorMessage);
        logger.debug(errorMessage, e);
    }


    /**
     * Logs a debug-level message.
     *
     * @param logger  the SLF4J {@link Logger} instance
     * @param message the message to log
     */
    public static void logDebug(Logger logger, String message) {
        logger.debug(LogUtil.format(message));
    }

    /**
     * Logs a debug-level message with an associated exception.
     * <p>
     * If {@code message} is {@code null} or empty, the exception's
     * message will be used instead.
     * </p>
     *
     * @param logger  the SLF4J {@link Logger} instance
     * @param message the message to log (may be {@code null})
     * @param e       the exception to log alongside the message
     */
    public static void logDebug(Logger logger, String message, Throwable e) {
        logger.debug(
                LogUtil.format(message == null || message.isEmpty() ? e.getMessage() : message),
                e
        );
    }

    /**
     * Logs an info-level message.
     *
     * @param logger  the SLF4J {@link Logger} instance
     * @param message the message to log
     */
    public static void logInfo(Logger logger, String message) {
        logger.info(LogUtil.format(message));
    }

    /**
     * Logs a warning-level message.
     *
     * @param logger  the SLF4J {@link Logger} instance
     * @param message the message to log
     */
    public static void logWarn(Logger logger, String message) {
        logger.warn(LogUtil.format(message));
    }

    /**
     * Logs an error-level message.
     *
     * @param logger  the SLF4J {@link Logger} instance
     * @param message the message to log
     */
    public static void logError(Logger logger, String message) {
        logger.error(LogUtil.format(message));
    }
}

