package phylax.iam.Signum.Token_Service.common.util.logging;

import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;


/**
 * Utility class for formatting log messages with a service-specific prefix.
 * <p>
 * This utility ensures that every log message is tagged with the current
 * application's service name (configured via {@link #init(String)}), making
 * it easier to trace logs in multi-service or distributed environments.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * LogUtil.init("Signum-Token-Service");
 * logger.info(LogUtil.format("Token successfully generated"));
 * }</pre>
 * </p>
 *
 * <p>
 * Example output:
 * <pre>
 * [Signum-Token-Service] Token successfully generated
 * </pre>
 * </p>
 *
 * <p><b>Note:</b> This is a utility class and cannot be instantiated.</p>
 *
 * @author
 *     Pragyanshu Rai
 */
public final class LogUtil {

    /**
     * The service name used as a prefix in formatted log messages.
     * <p>
     * Typically initialized from the application's configuration
     * (e.g., {@code spring.application.name}).
     * </p>
     */
    private static String serviceName;

    /**
     * Private constructor to prevent instantiation.
     * <p>
     * Throws {@link IllegalInstantiationException} if called reflectively.
     * </p>
     */
    private LogUtil() {
        throw new IllegalInstantiationException();
    }

    /**
     * Initializes the utility with the provided service name.
     * <p>
     * This should be called once during application startup
     * to configure the log prefix.
     * </p>
     *
     * @param serviceName the name of the service to use in log message prefixes
     */
    public static void init(String serviceName) {
        LogUtil.serviceName = serviceName;
    }

    /**
     * Formats the given message by prefixing it with the configured
     * {@link #serviceName}.
     * <p>
     * If the service name has not been initialized, this will still
     * format the message, but the prefix may be {@code null}.
     * </p>
     *
     * @param message the log message to format
     * @return a formatted string in the form {@code [serviceName] message}
     */
    public static String format(String message) {
        return String.format("[%s] %s", LogUtil.serviceName, message);
    }
}

