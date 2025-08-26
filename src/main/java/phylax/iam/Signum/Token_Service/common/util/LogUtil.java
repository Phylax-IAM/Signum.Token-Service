package phylax.iam.Signum.Token_Service.common.util;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


/**
 * Utility component for formatting log messages with a service-specific prefix.
 * <p>
 * This utility prepends the current application's service name
 * (configured via {@code spring.application.name}) to each log message,
 * allowing for easier identification of logs in multi-service or
 * distributed environments.
 * </p>
 *
 * <p>
 * Example output:
 * <pre>
 * [Signum-Token-Service] Token successfully generated
 * </pre>
 * </p>
 *
 * @author Pragyanshu Rai
 */
@Component
public class LogUtil {

    /**
     * Name of the service, injected from the Spring application configuration.
     * <p>
     * Defaults to {@code Signum-Token-Service} if the property
     * {@code spring.application.name} is not explicitly defined.
     * </p>
     */
    @Value("${spring.application.name:Signum-Token-Service}")
    private String serviceName;

    /**
     * Formats the given message by prefixing it with the current
     * {@link #serviceName}.
     *
     * @param message the log message to format
     * @return a formatted string in the form
     *         {@code [serviceName] message}
     */
    public String format(String message) {
        return String.format("[%s] %s", this.serviceName, message);
    }
}
